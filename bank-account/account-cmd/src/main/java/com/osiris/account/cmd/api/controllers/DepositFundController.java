package com.osiris.account.cmd.api.controllers;

import com.osiris.account.cmd.api.commands.DepositFundCommand;
import com.osiris.account.common.dto.BaseResponse;
import com.osiris.messaging.exceptions.AggregateNotFoundException;
import com.osiris.messaging.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    private final CommandDispatcher commandDispatcher;

    @Autowired
    public DepositFundController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id, @RequestBody DepositFundCommand command){
        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully!!!"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException ex){
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            var message = MessageFormat.format("Error while processing request to deposit funds to bank account for id - {0}", id);
            logger.log(Level.SEVERE, message, ex);
            return new ResponseEntity<>(new BaseResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
