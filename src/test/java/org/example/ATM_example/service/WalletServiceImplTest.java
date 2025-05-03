package org.example.ATM_example.service;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;
import org.example.ATM_example.exception.WalletNotFoundException;
import org.example.ATM_example.handler.OperationFactory;
import org.example.ATM_example.handler.OperationHandler;
import org.example.ATM_example.mapper.WalletMapper;
import org.example.ATM_example.model.OperationType;
import org.example.ATM_example.model.Wallet;
import org.example.ATM_example.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.example.ATM_example.testData.WalletTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @Mock
    private OperationFactory operationFactory;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletMapper walletMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void whenPerformOperationThenWalletBalanceResponseDto() {
        UUID walletId = WALLET_UUID_FROM_DB;
        WalletOperationRequestDto request = createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit();
        OperationHandler mockHandler = mock(OperationHandler.class);
        WalletBalanceResponseDto expectedResponse = createWalletBalanceResponseDto();

        when(operationFactory.getHandler(OperationType.DEPOSIT)).thenReturn(mockHandler);
        when(mockHandler.execute(walletId, request.getAmount())).thenReturn(expectedResponse);

        WalletBalanceResponseDto actual = walletService.performOperation(request);

        verify(operationFactory).getHandler(OperationType.DEPOSIT);
        verify(mockHandler).execute(walletId, request.getAmount());
        assertSame(expectedResponse, actual);
    }

    @Test
    void whenGetBalanceThenWalletBalanceResponseDto() {
        UUID walletId = WALLET_UUID_NOT_FROM_DB;
        Wallet wallet = createWallet();
        WalletBalanceResponseDto expectedDto = createWalletBalanceResponseDto();

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletMapper.fromWalletToWalletBalanceResponseDto(wallet)).thenReturn(expectedDto);

        WalletBalanceResponseDto actual = walletService.getBalance(walletId);
        verify(walletRepository).findById(walletId);
        verify(walletMapper).fromWalletToWalletBalanceResponseDto(wallet);
        assertEquals(expectedDto, actual);

    }

    @Test
    void whenGetBalanceWithNotExistingWalletIdThenThrowException() {
        UUID walletId = WALLET_UUID_NOT_FROM_DB;
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        assertThrows(WalletNotFoundException.class, () -> walletService.getBalance(walletId));
    }
}