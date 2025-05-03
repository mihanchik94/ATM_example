package org.example.ATM_example.handler;

import lombok.RequiredArgsConstructor;
import org.example.ATM_example.annotation.LogOperation;
import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.exception.WithdrawOperationException;
import org.example.ATM_example.model.OperationType;
import org.example.ATM_example.repository.WalletRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WithdrawOperationHandler implements OperationHandler {
    private final WalletRepository walletRepository;

    @Override
    public OperationType getOperationType() {
        return OperationType.WITHDRAW;
    }

    @LogOperation("Execute withdraw operation")
    @Override
    public WalletBalanceResponseDto execute(UUID walletId, BigDecimal amount) {
        return walletRepository.withdraw(walletId, amount)
                .map(WalletBalanceResponseDto::new)
                .orElseThrow(() -> new WithdrawOperationException(
                        String.format("Withdraw operation is failed. Check entered wallet id: %s and balance", walletId)
                ));
    }
}
