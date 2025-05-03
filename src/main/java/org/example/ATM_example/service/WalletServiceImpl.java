package org.example.ATM_example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ATM_example.dto.WalletBalanceResponseDto;
import org.example.ATM_example.dto.WalletOperationRequestDto;
import org.example.ATM_example.exception.WalletNotFoundException;
import org.example.ATM_example.handler.OperationFactory;
import org.example.ATM_example.handler.OperationHandler;
import org.example.ATM_example.mapper.WalletMapper;
import org.example.ATM_example.model.Wallet;
import org.example.ATM_example.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final OperationFactory operationFactory;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public WalletBalanceResponseDto performOperation(WalletOperationRequestDto request) {
        UUID walletUUID = UUID.fromString(request.getWalletId());
        OperationHandler handler = operationFactory.getHandler(request.getOperationType());
        return handler.execute(walletUUID, request.getAmount());
    }

    @Override
    public WalletBalanceResponseDto getBalance(UUID walletId) {
        return walletMapper.fromWalletToWalletBalanceResponseDto(findById(walletId));
    }

    private Wallet findById(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(() ->
                new WalletNotFoundException(String.format("Wallet with id: %s not found", walletId)));
    }
}
