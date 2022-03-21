package com.osiris.account.cmd.infrastructure;

import com.osiris.account.cmd.domain.AccountAggregate;
import com.osiris.messaging.domain.AggregateRoot;
import com.osiris.messaging.events.BaseEvent;
import com.osiris.messaging.handlers.EventSourcingHandler;
import com.osiris.messaging.infrastructure.EventStore;
import com.osiris.messaging.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private EventStore eventStore;
    private EventProducer eventProducer;

    @Autowired
    public AccountEventSourcingHandler(EventStore eventStore, EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if(events != null && !events.isEmpty()){
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for(var aggregateId : aggregateIds){
            var aggregate = getById(aggregateId);
            if(aggregate==null || !aggregate.getActive())
                continue;
            var events = eventStore.getEvents(aggregateId);
            for(var event : events){
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
