package com.osiris.account.cmd.api.commands;

import com.osiris.account.common.dto.AccountType;
import com.osiris.messaging.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
