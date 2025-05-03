package org.example.ATM_example.handler;

import org.example.ATM_example.model.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OperationFactoryTest {

    @Mock
    private OperationHandler depositHandler;

    @Mock
    private OperationHandler withdrawHandler;

    @InjectMocks
    private OperationFactory operationFactory;

    @Test
    void whenGetHandlerForExistingTypeThenReturnHandler() {
        Map<OperationType, OperationHandler> handlers = Map.of(
                OperationType.DEPOSIT, depositHandler,
                OperationType.WITHDRAW, withdrawHandler
        );

        operationFactory = new OperationFactory(handlers);

        assertSame(depositHandler, operationFactory.getHandler(OperationType.DEPOSIT));
        assertSame(withdrawHandler, operationFactory.getHandler(OperationType.WITHDRAW));
    }

    @Test
    void whenGetHandlerForUnknownTypeThenThrowException() {
        Map<OperationType, OperationHandler> emptyHandlers = Collections.emptyMap();
        operationFactory = new OperationFactory(emptyHandlers);
        assertThrows(UnsupportedOperationException.class, () -> operationFactory.getHandler(OperationType.DEPOSIT));
    }
}