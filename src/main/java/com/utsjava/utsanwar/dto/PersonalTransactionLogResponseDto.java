package com.utsjava.utsanwar.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTransactionLogResponseDto {
    private String accountNumber;
    private String customerName;
    private int totalExpense;
    private int totalIncome;
}
