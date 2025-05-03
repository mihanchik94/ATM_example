package org.example.ATM_example.handler;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.exception.WithdrawOperationException;
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
class WithdrawOperationHandlerTest {
    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WithdrawOperationHandler withdrawOperationHandler;

    @Test
    void whenGetOperationTypeThenOperationTypeDEPOSIT() {
        OperationType actual = withdrawOperationHandler.getOperationType();
        assertEquals(OperationType.WITHDRAW, actual);
    }

    @Test
    void whenExecuteWithExistingIdThenWalletBalanceResponseDto() {
        BigDecimal expectedBalance = BigDecimal.TEN;
        when(walletRepository.withdraw(WALLET_UUID_NOT_FROM_DB, expectedBalance)).thenReturn(Optional.of(BigDecimal.TEN));
        WalletBalanceResponseDto actual = withdrawOperationHandler.execute(WALLET_UUID_NOT_FROM_DB, expectedBalance);
        assertEquals(expectedBalance, actual.balance());
    }

    @Test
    void whenExecuteWithNotExistingIdThenException() {
        when(walletRepository.withdraw(any(), any())).thenReturn(Optional.empty());
        assertThrows(WithdrawOperationException.class, () -> withdrawOperationHandler.execute(WALLET_UUID_NOT_FROM_DB, BigDecimal.TEN));
    }

}