package com.utsjava.utsanwar.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTransactionLogResponseDtoDeviation {
    private String accountNumber;
    private String customerName;
    private int totalIncome;
    private int totalExpense;
    private int deviation;
    private String conclussion;
}
