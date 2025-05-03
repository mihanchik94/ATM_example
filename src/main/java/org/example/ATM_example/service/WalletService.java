package org.example.ATM_example.service;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;

import java.util.UUID;

public interface WalletService {
    WalletBalanceResponseDto performOperation(WalletOperationRequestDto request);
    WalletBalanceResponseDto getBalance(UUID walletId);
}
