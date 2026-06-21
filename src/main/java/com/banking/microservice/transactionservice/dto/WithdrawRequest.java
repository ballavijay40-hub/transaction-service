package com.banking.microservice.transactionservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
public class WithdrawRequest {

    @NotNull(message = "Account id is requilred.")
    @Positive(message = "Account id must be positive.")
    private Long accountId;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.01", message = "amount must be positive")
    @Digits(integer = 17, fraction = 2, message = "Amount can have max 17 digits and 2 decimals.")
    private BigDecimal amount;

    @Size(max = 255, message = "Description cannot exceed 255 characers.")
    private String description;
}
