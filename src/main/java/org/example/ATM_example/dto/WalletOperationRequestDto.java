package org.example.ATM_example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ATM_example.model.OperationType;

import java.math.BigDecimal;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class WalletOperationRequestDto {
    @NotNull(message = "Wallet id is mandatory")
    @Schema(example = "601f41a2-f850-4e7a-b1ac-096f3202dfcd", description = "Wallet id")
    @org.hibernate.validator.constraints.UUID
            (message = "Wallet Id should have uuid format.")
    private String walletId;


    @NotNull(message = "Operation type is mandatory")
    @Schema(description = "Operation type", allowableValues = {"DEPOSIT", "WITHDRAW"})
    private OperationType operationType;

    @Positive(message = "Amount must be greater than 0")
    @NotNull(message = "Amount is mandatory")
    @Schema(example = "10000.00", description = "Operation amount")
    private BigDecimal amount;

}
