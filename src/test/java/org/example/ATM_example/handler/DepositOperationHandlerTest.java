package org.example.ATM_example.handler;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.exception.WalletNotFoundException;
import org.example.ATM_example.model.OperationType;
import org.example.ATM_example.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.example.ATM_example.testData.WalletTestData.WALLET_UUID_NOT_FROM_DB;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositOperationHandlerTest {
    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private DepositOperationHandler depositOperationHandler;

    @Test
    void whenGetOperationTypeThenOperationTypeDEPOSIT() {
        OperationType actual = depositOperationHandler.getOperationType();
        assertEquals(OperationType.DEPOSIT, actual);
    }

    @Test
    void whenExecuteWithExistingIdThenWalletBalanceResponseDto() {
        BigDecimal expectedBalance = BigDecimal.TEN;
        when(walletRepository.deposit(WALLET_UUID_NOT_FROM_DB, expectedBalance)).thenReturn(Optional.of(BigDecimal.TEN));
        WalletBalanceResponseDto actual = depositOperationHandler.execute(WALLET_UUID_NOT_FROM_DB, expectedBalance);
        assertEquals(expectedBalance, actual.balance());
    }

    @Test
    void whenExecuteWithNotExistingIdThenException() {
        when(walletRepository.deposit(any(), any())).thenReturn(Optional.empty());
        assertThrows(WalletNotFoundException.class, () -> depositOperationHandler.execute(WALLET_UUID_NOT_FROM_DB, BigDecimal.TEN));
    }
}