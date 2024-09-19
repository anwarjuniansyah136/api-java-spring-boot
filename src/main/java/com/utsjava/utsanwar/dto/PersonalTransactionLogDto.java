package com.utsjava.utsanwar.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTransactionLogDto {
    private String id;
    private String accountNumber;
    private String customerName;
    private String transactionType;
    private LocalDateTime transactionDate;
    private int amount;
    private String description;
}
