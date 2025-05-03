package org.example.ATM_example.handler;

import lombok.RequiredArgsConstructor;
import org.example.ATM_example.annotation.LogOperation;
import org.example.ATM_example.model.OperationType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OperationFactory {
    private final Map<OperationType, OperationHandler> handlerMap;

    @LogOperation("Get operation handler")
    public OperationHandler getHandler(OperationType type) {
        return Optional.ofNullable(handlerMap.get(type))
                .orElseThrow(() -> new UnsupportedOperationException("No handler found for operation type: " + type));
    }
}
