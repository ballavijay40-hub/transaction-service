package com.banking.microservice.transactionservice.event;

import com.banking.microservice.transactionservice.enums.TransactionType;
import lombok.*;
import java.math.BigDecimal;


//this is fromm transactio to account ---
//producer i transaction

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestEvent {

    private String referenceNumber;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;

    private TransactionType transactionType;
}
