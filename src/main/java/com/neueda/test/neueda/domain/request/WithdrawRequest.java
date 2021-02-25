package com.neueda.test.neueda.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {
    private String accountNumber;
    private int pin;
    private int withdrawAmount;
}
