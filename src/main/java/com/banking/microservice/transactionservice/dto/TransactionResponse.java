package com.banking.microservice.transactionservice.dto;


import com.banking.microservice.transactionservice.enums.TransactionStatus;
import com.banking.microservice.transactionservice.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {


    private Long id;

    private String referenceNumber;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;

    private TransactionType transactionType;

    private TransactionStatus status;

    private String description;

    private LocalDateTime createdAt;

    private String failureReason;



}
