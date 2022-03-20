package com.osiris.account.cmd.api.controllers;

import com.osiris.account.cmd.api.commands.OpenAccountCommand;
import com.osiris.account.cmd.api.dto.OpenAccountResponse;
import com.osiris.account.common.dto.BaseResponse;
import com.osiris.messaging.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    private final CommandDispatcher commandDispatcher;

    @Autowired
    public OpenAccountController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account creation request completed successfully!!!", id), HttpStatus.CREATED);
        } catch (IllegalStateException ex){
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            var message = MessageFormat.format("Error while processing request to open a new bank account for id - {0}", id);
            logger.log(Level.SEVERE, message, ex);
            return new ResponseEntity<>(new OpenAccountResponse(message, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
