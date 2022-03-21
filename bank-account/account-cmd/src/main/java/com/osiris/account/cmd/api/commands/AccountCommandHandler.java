package com.osiris.account.cmd.api.commands;

import com.osiris.account.cmd.domain.AccountAggregate;
import com.osiris.messaging.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler{
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Autowired
    public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        if(command.getAmount() > aggregate.getBalance()){
            throw new IllegalStateException("Withdrawal declined, insufficient funds!");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(RestoreReadDBCommand command) {
        eventSourcingHandler.republishEvents();
    }
}
