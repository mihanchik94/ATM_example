package org.example.ATM_example.handler;

import lombok.RequiredArgsConstructor;
import org.example.ATM_example.annotation.LogOperation;
import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.exception.WalletNotFoundException;
import org.example.ATM_example.model.OperationType;
import org.example.ATM_example.repository.WalletRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DepositOperationHandler implements OperationHandler {
    private final WalletRepository walletRepository;

    @Override
    public OperationType getOperationType() {
        return OperationType.DEPOSIT;
    }

    @LogOperation("Execute deposit operation")
    @Override
    public WalletBalanceResponseDto execute(UUID walletId, BigDecimal amount) {
        return walletRepository.deposit(walletId, amount)
                .map(WalletBalanceResponseDto::new)
                .orElseThrow(() -> new WalletNotFoundException(String.format("Wallet with id: %s not found", walletId)));
    }
}
