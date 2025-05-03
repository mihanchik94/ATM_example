package org.example.ATM_example.controller;

import lombok.SneakyThrows;
import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;
import org.example.ATM_example.exception.WalletNotFoundException;
import org.junit.jupiter.api.Test;;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.example.ATM_example.testData.WalletTestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletControllerTest extends BaseApiControllerTest {
    @SneakyThrows
    @Test
    void whenGetBalanceWithExistingUUIDThenReturn200() {
        UUID walletUUID = WALLET_UUID_NOT_FROM_DB;
        WalletBalanceResponseDto walletBalanceResponseDto = createWalletBalanceResponseDto();

        when(walletService.getBalance(walletUUID)).thenReturn(walletBalanceResponseDto);
        performGetRequest(walletUUID)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        verify(walletService, times(1)).getBalance(walletUUID);
    }

    @SneakyThrows
    @Test
    void whenGetBalanceWithNotExistingUUIDThenReturn404() {
        UUID walletUUID = WALLET_UUID_NOT_FROM_DB;

        when(walletService.getBalance(walletUUID)).thenThrow(WalletNotFoundException.class);
        performGetRequest(walletUUID)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        verify(walletService, times(1)).getBalance(walletUUID);
    }

    @SneakyThrows
    @Test
    void whenUpdateBalanceWithExistingUUIDThenReturn200() {
        WalletBalanceResponseDto walletBalanceResponseDto = createWalletBalanceResponseDto();
        WalletOperationRequestDto walletOperationRequestDto = createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit();

        when(walletService.performOperation(walletOperationRequestDto)).thenReturn(walletBalanceResponseDto);
        performPostRequest(walletOperationRequestDto)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        verify(walletService, times(1)).performOperation(walletOperationRequestDto);
    }

    @SneakyThrows
    @Test
    void whenUpdateBalanceWithNotExistingUUIDThenReturn404() {
        WalletOperationRequestDto walletOperationRequestDto = createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit();

        when(walletService.performOperation(walletOperationRequestDto)).thenThrow(WalletNotFoundException.class);
        performPostRequest(walletOperationRequestDto)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        verify(walletService, times(1)).performOperation(walletOperationRequestDto);
    }

    @SneakyThrows
    @Test
    void whenUpdateBalanceWithWrongUUIDThenReturn400() {
        WalletOperationRequestDto walletOperationRequestDto = createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit();
        walletOperationRequestDto.setWalletId(STR_WRONG_UUID);

        performPostRequest(walletOperationRequestDto)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        verify(walletService, never()).performOperation(any());
    }

    @SneakyThrows
    @Test
    void whenUpdateBalanceWithNegativeBalanceThenReturn400() {
        WalletOperationRequestDto walletOperationRequestDto = createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit();
        walletOperationRequestDto.setAmount(BigDecimal.valueOf(-10L));

        performPostRequest(walletOperationRequestDto)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andReturn();

        verify(walletService, never()).performOperation(any());
    }


    private ResultActions performGetRequest(UUID walletId) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(GET_WALLET_BALANCE_URL_TEMPLATE, walletId)
                .contentType(APPLICATION_JSON)
        );
    }

    private ResultActions performPostRequest(WalletOperationRequestDto request) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post(UPDATE_WALLET_BALANCE_URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
    }

}