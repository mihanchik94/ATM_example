package org.example.ATM_example.mapper;

import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    WalletBalanceResponseDto fromWalletToWalletBalanceResponseDto(Wallet wallet);
}
