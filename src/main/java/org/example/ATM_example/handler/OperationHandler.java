package org.example.ATM_example.handler;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.model.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public interface OperationHandler {
    OperationType getOperationType();
    WalletBalanceResponseDto execute(UUID walletId, BigDecimal amount);
}
