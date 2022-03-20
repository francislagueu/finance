package com.osiris.account.cmd.infrastructure;

import com.osiris.account.cmd.domain.AccountAggregate;
import com.osiris.account.cmd.domain.EventStoreRepository;
import com.osiris.messaging.events.BaseEvent;
import com.osiris.messaging.events.EventModel;
import com.osiris.messaging.exceptions.AggregateNotFoundException;
import com.osiris.messaging.exceptions.ConcurrencyException;
import com.osiris.messaging.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;

    @Autowired
    public AccountEventStore(EventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion){
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for(var event : events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if(persistedEvent != null){
                //TODO: produce event to Kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }
        return eventStream.stream().map(EventModel::getEventData).toList();
    }
}
