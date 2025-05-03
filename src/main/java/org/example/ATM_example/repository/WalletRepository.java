package org.example.ATM_example.repository;

import org.example.ATM_example.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Query(value = """
            update wallets 
            set balance = balance + :amount 
            where id = :id 
            returning balance
            """, nativeQuery = true)
    Optional<BigDecimal> deposit(@Param("id") UUID id, @Param("amount") BigDecimal amount);

    @Query(value = """
            update wallets 
            set balance = balance - :amount 
            where id = :id AND balance >= :amount 
            returning balance
            """, nativeQuery = true)
    Optional<BigDecimal> withdraw(@Param("id") UUID id, @Param("amount") BigDecimal amount);

}
