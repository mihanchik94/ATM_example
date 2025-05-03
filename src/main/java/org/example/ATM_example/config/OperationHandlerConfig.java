package org.example.ATM_example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.ATM_example.handler.OperationHandler;
import org.example.ATM_example.model.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class OperationHandlerConfig {
    @Autowired
    private List<OperationHandler> handlers;

    @Bean
    public Map<OperationType, OperationHandler> operationHandlers() {
        log.info("Initializing operation handlers. Found {} handlers", handlers.size());
        Map<OperationType, OperationHandler> operationHandlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        OperationHandler::getOperationType,
                        Function.identity()
                ));
        log.debug("Registered handlers: {}", operationHandlerMap.keySet());
        return operationHandlerMap;
    }
}
