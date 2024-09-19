package com.utsjava.utsanwar.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTransactionLogResponseDtoDaily {
    private String accountNumber;
    private String customerName;
    private String periode;
    private int totalExpense;
    private int totalIncome;
}
