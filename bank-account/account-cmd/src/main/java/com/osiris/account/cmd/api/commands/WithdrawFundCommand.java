package com.osiris.account.cmd.api.commands;

import com.osiris.messaging.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundCommand extends BaseCommand {
    private double amount;
}
