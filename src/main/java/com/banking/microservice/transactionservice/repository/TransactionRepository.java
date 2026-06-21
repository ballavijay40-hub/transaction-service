package com.banking.microservice.transactionservice.repository;

import com.banking.microservice.transactionservice.dto.TransactionResponse;
import com.banking.microservice.transactionservice.entity.Transaction;
import com.banking.microservice.transactionservice.enums.TransactionStatus;
import com.banking.microservice.transactionservice.enums.TransactionType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Optional<TransactionResponse> findByReferenceNumber(String referenceNUmber);

    boolean existsByReferenceNumber(String referenceNumber);

    Page<Transaction> findBYAromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId, Pageable pageable);

    Page<Transaction> findByTransactionTtype(TransactionType type,Pageable pageable);

    Page<Transaction> findByTransactionStatus(TransactionStatus status,Pageable pageable);

    Page<Transaction> findByCreatedAt(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


    @Query(""" 
                select t 
                from Transaction t
                where (t.fromAccountId=:accountId
                        or t.toAccountId= :accountId)
                and t.createdAt between :startDate and :endDate
                
           """)
    Page<Transaction> findAccountTransactionsBetweenDates(
            @Param("accountId") Long accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );




}
