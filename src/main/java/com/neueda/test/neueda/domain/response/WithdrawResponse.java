package com.neueda.test.neueda.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawResponse {
    private int returnAmount;
    private int fiftyCount;
    private int twentyCount;
    private int tenCount;
    private int fiveCount;
}
