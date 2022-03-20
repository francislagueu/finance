package com.osiris.account.cmd.api.commands;

import com.osiris.messaging.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundCommand extends BaseCommand {
    private double amount;
}
