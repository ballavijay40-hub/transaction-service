package com.banking.microservice.transactionservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
public class TransferRequest {

    @NotNull(message = "Source Account id ie required.")
    @Positive(message = "Source Account id must be positive.")
    private Long fromAccountId;

    @NotNull(message="Destination Account id required.")
    @Positive(message="Destinatoin Account id must be positive.")
    private  Long toAccountId;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value="0.01",message="Transfer ammount must be greater than 0.")
    @Digits(integer=17,fraction=2,message="Invalid amount format.")
    private BigDecimal amount;

    @Size(max=255,message="Description cannot exceed 255 characters.")
    private String description;




}
