package org.example.ATM_example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;
import org.example.ATM_example.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "Wallet Controller", description = "API for working with 'wallet' information")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class WalletController {
    private final WalletService walletService;

    @Operation(summary = "Get wallet balance by uuid", responses = {
            @ApiResponse(responseCode = "200", description = "Wallet was found"),
            @ApiResponse(responseCode = "404", description = "Wallet is not exist")
    })
    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletBalanceResponseDto> getBalance(@PathVariable("walletId") UUID walletId) {
        return new ResponseEntity<>(walletService.getBalance(walletId), HttpStatus.OK);
    }

    @Operation(summary = "Update Wallet balance", responses = {
            @ApiResponse(responseCode = "200", description = "Operation completed successfully"),
            @ApiResponse(responseCode = "400", description = "The request data is incorrect"),
            @ApiResponse(responseCode = "404", description = "Wallet is not exist")
    })
    @PostMapping("/wallet")
    public ResponseEntity<WalletBalanceResponseDto> updateBalance(
            @RequestBody @Valid WalletOperationRequestDto walletOperationRequestDto) {
        return new ResponseEntity<>(walletService.performOperation(walletOperationRequestDto), HttpStatus.OK);
    }
 }
