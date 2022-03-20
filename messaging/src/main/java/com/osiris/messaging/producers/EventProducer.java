package com.osiris.messaging.producers;

import com.osiris.messaging.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
