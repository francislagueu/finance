package com.osiris.account.query.infrastructure.consumers;

import com.osiris.account.common.events.AccountClosedEvent;
import com.osiris.account.common.events.AccountOpenedEvent;
import com.osiris.account.common.events.FundDepositedEvent;
import com.osiris.account.common.events.FundWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
