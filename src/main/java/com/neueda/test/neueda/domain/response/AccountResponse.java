package com.neueda.test.neueda.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private long openBalance;
    private long maxWithdrawBalance;

    @Override
    public String toString() {
        return "Hello User, Account Balance=" + openBalance +
                ", Maximum Withdrawal Amount=" + maxWithdrawBalance;
    }
}
