package com.osiris.messaging.handlers;

import com.osiris.messaging.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    T getById(String id);
}
