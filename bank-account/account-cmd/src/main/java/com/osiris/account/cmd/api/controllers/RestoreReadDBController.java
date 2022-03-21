package com.osiris.account.cmd.api.controllers;

import com.osiris.account.cmd.api.commands.RestoreReadDBCommand;
import com.osiris.account.common.dto.BaseResponse;
import com.osiris.messaging.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDB")
public class RestoreReadDBController {
    private final Logger logger = Logger.getLogger(RestoreReadDBController.class.getName());

    private final CommandDispatcher commandDispatcher;

    @Autowired
    public RestoreReadDBController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDB(){

        try{
            commandDispatcher.send(new RestoreReadDBCommand());
            return new ResponseEntity<>(new BaseResponse("Read database restore request completed successfully!!!"), HttpStatus.CREATED);
        } catch (IllegalStateException ex){
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
            return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            var message ="Error while processing request to restore read database";
            logger.log(Level.SEVERE, message, ex);
            return new ResponseEntity<>(new BaseResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
