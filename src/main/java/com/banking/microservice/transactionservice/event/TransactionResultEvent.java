package com.banking.microservice.transactionservice.event;
import com.banking.microservice.transactionservice.enums.TransactionStatus;
import com.banking.microservice.transactionservice.enums.TransactionType;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResultEvent {

    private String referenceNumber;

    private TransactionStatus status;

    private String failureReason;


    }
