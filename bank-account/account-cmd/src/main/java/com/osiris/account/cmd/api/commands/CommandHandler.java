package com.osiris.account.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFundCommand command);
    void handle(WithdrawFundCommand command);
    void handle(CloseAccountCommand command);
    void handle(RestoreReadDBCommand command);
}
