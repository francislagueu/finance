package com.osiris.account.cmd.domain;

import com.osiris.account.cmd.api.commands.OpenAccountCommand;
import com.osiris.account.common.events.AccountClosedEvent;
import com.osiris.account.common.events.AccountOpenedEvent;
import com.osiris.account.common.events.FundDepositedEvent;
import com.osiris.account.common.events.FundWithdrawnEvent;
import com.osiris.messaging.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public double getBalance() {
        return this.balance;
    }

    public boolean getActive(){
        return this.active;
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }
        if(amount <= 0){
            throw new IllegalStateException("The deposit amount must be greater than 0!");
        }
        raiseEvent(FundDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }
        if(amount >= this.balance){
            throw new IllegalStateException("The withdraw amount must be less than the account balance!");
        }
        raiseEvent(FundWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if(!this.active){
            throw new IllegalStateException("The bank account has already been closed!");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }
}
