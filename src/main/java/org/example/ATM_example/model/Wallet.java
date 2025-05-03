package org.example.ATM_example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Builder(setterPrefix = "with")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    private UUID id;

    @Column(name = "balance", nullable = false, columnDefinition = "NUMERIC(15,2) DEFAULT 0.0")
    private BigDecimal balance;

}
