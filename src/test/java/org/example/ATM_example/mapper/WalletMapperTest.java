package org.example.ATM_example.mapper;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.model.Wallet;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.example.ATM_example.testData.WalletTestData.createWallet;
import static org.junit.jupiter.api.Assertions.*;

class WalletMapperTest {

    private final WalletMapper walletMapper = Mappers.getMapper(WalletMapper.class);

    @Test
    void whenFromWalletToWalletBalanceResponseDtoThenWalletBalanceResponseDto() {
        Wallet wallet = createWallet();
        WalletBalanceResponseDto actual = walletMapper.fromWalletToWalletBalanceResponseDto(wallet);
        assertNotNull(actual);
        assertEquals(wallet.getBalance(), actual.balance());
    }
}