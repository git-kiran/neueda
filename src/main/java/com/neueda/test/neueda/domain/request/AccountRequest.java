package com.neueda.test.neueda.domain.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NotNull(message = "Value cannot be null")
    private String accountNumber;
    @PositiveOrZero
    private int pin;
}
