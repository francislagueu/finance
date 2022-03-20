package com.osiris.account.query.infrastructure.handlers;

import com.osiris.account.common.events.AccountClosedEvent;
import com.osiris.account.common.events.AccountOpenedEvent;
import com.osiris.account.common.events.FundDepositedEvent;
import com.osiris.account.common.events.FundWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundDepositedEvent event);
    void on(FundWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
