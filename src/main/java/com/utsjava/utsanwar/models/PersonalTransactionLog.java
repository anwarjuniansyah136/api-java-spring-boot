package com.utsjava.utsanwar.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personal_transaction_log")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTransactionLog {
    @Id
    @UuidGenerator
    @Column(length = 36)
    private String id;

    @Column(name = "account_number", length = 15)
    private String accountNumber;

    @Column(name = "customer_name", length = 500)
    private String customerName;

    @Column(name = "transaction_type", length = 10)
    private String transactionType;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "amount")
    private int amount;

    @Column(name = "description", length = 500)
    private String description;
}
