package com.banking.microservice.transactionservice.entity;


import com.banking.microservice.transactionservice.enums.TransactionStatus;
import com.banking.microservice.transactionservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions",indexes = {
        @Index(name="idx_reference_id",columnList = "reference_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,updatable = false,length=50,name="reference_id")
    private String referenceNumber;

    @Column(name="from_account_id")
    private Long fromAccountId;

    @Column(name="to_account_id")
    private Long toAccountId;

    @Column(nullable = false,precision=19,scale=2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=25)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=25)
    @Builder.Default
    private TransactionStatus status=TransactionStatus.PENDING;

    @Column(length=255)
    private String description;

    @Column(nullable = false,updatable = false,name="created_at")
    private LocalDateTime createdAt;
    @Column(name="failure_reason",length = 255)
    private String failureReason;

    @PrePersist
   public void onCreate(){
        this.createdAt=LocalDateTime.now();

   }














}
