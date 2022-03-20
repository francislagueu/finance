package com.osiris.account.common.events;

import com.osiris.messaging.events.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent  extends BaseEvent {
}
