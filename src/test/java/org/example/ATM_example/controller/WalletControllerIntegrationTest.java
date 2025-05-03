package org.example.ATM_example.controller;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.example.ATM_example.testData.WalletTestData.*;
import static org.example.ATM_example.testData.WalletTestData.createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit;

public class WalletControllerIntegrationTest extends BaseApiControllerIntegrationTest {
    @Test
    void whenGetBalanceWithExistingUUIDThenReturn200AndJsonResponse() {
        ResponseEntity<WalletBalanceResponseDto> response = getBalance(WALLET_UUID_FROM_DB);
        assertResponse(response, HttpStatus.OK, MediaType.APPLICATION_JSON);
    }

    @Test
    void whenGetBalanceWithNotExistingUUIDThenReturn404JsonResponse() {
        ResponseEntity<WalletBalanceResponseDto> response = getBalance(WALLET_UUID_NOT_FROM_DB);
        assertResponse(response, HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON);
    }

    @Test
    void whenUpdateBalanceWithExistingUUIDThenReturn200() {
        ResponseEntity<WalletBalanceResponseDto> response = updateBalance(req -> {});
        assertResponse(response, HttpStatus.OK, MediaType.APPLICATION_JSON);
    }

    @Test
    void whenUpdateBalanceWithNotExistingUUIDThenReturn404() {
        ResponseEntity<WalletBalanceResponseDto> response = updateBalance(req ->
                req.setWalletId(STR_WALLET_UUID_NOT_FROM_DB)
        );
        assertResponse(response, HttpStatus.NOT_FOUND, MediaType.APPLICATION_JSON);
    }


    @Test
    void whenUpdateBalanceWithWrongUUIDThenReturn400() {
        ResponseEntity<WalletBalanceResponseDto> response = updateBalance(req ->
                req.setWalletId(STR_WRONG_UUID)
        );
        assertResponse(response, HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON);
    }

    @Test
    void whenUpdateBalanceWithNegativeBalanceThenReturn400() {
        ResponseEntity<WalletBalanceResponseDto> response = updateBalance(req ->
                req.setAmount(BigDecimal.valueOf(-10L))
        );
        assertResponse(response, HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON);
    }


    private ResponseEntity<WalletBalanceResponseDto> getBalance(UUID walletId) {
        return restTemplate.exchange(
                GET_WALLET_BALANCE_URL_TEMPLATE,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                WalletBalanceResponseDto.class,
                walletId
        );
    }

    private ResponseEntity<WalletBalanceResponseDto> updateBalance(Consumer<WalletOperationRequestDto> requestCustomizer) {
        WalletOperationRequestDto request = createWalletOperationRequestDtoWithCorrectDataAndTypeDeposit();
        requestCustomizer.accept(request);
        HttpEntity<WalletOperationRequestDto> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(
                UPDATE_WALLET_BALANCE_URL_TEMPLATE,
                HttpMethod.POST,
                requestEntity,
                WalletBalanceResponseDto.class
        );
    }

    private void assertResponse(ResponseEntity<?> response, HttpStatus expectedStatus, MediaType expectedContentType) {
        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedContentType, response.getHeaders().getContentType());
    }
}
