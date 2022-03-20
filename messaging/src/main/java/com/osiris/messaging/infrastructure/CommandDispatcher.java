package com.osiris.messaging.infrastructure;

import com.osiris.messaging.commands.BaseCommand;
import com.osiris.messaging.commands.CommandHandlerMethod;
import org.springframework.stereotype.Component;

@Component
public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
