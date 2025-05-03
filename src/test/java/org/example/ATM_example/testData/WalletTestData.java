package org.example.ATM_example.testData;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;
import org.example.ATM_example.model.OperationType;
import org.example.ATM_example.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public final class WalletTestData {

    public static final UUID WALLET_UUID_NOT_FROM_DB = UUID.fromString("9a9b9b5c-1a2b-3c4d-5e6f-7a8b9c0d1e4e");
    public static final String STR_WALLET_UUID_NOT_FROM_DB = "9a9b9b5c-1a2b-3c4d-5e6f-7a8b9c0d1e4e";
    public static final UUID WALLET_UUID_FROM_DB = UUID.fromString("1a2b3b4c-5a6b-7c8d-5e6f-7a8b9c0d1e3e");
    public static final String STR_WALLET_UUID_FROM_DB = "1a2b3b4c-5a6b-7c8d-5e6f-7a8b9c0d1e3e";
    public static final String STR_WRONG_UUID = "1234";
    public static String GET_WALLET_BALANCE_URL_TEMPLATE = "/api/v1/wallets/{walletId}";
    public static String UPDATE_WALLET_BALANCE_URL_TEMPLATE = "/api/v1/wallet";

    private WalletTestData() {
    }

    public static Wallet createWallet() {
        return Wallet.builder()
                .withId(WALLET_UUID_NOT_FROM_DB)
                .withBalance(BigDecimal.TEN)
                .build();
    }

    public static WalletOperationRequestDto createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit() {
        return WalletOperationRequestDto.builder()
                .withWalletId(STR_WALLET_UUID_FROM_DB)
                .withAmount(BigDecimal.TEN)
                .withOperationType(OperationType.DEPOSIT)
                .build();
    }

    public static WalletBalanceResponseDto createWalletBalanceResponseDto() {
        return new WalletBalanceResponseDto(BigDecimal.TEN);
    }
}
