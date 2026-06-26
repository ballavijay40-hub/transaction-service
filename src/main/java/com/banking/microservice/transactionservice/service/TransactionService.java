package com.banking.microservice.transactionservice.service;

import com.banking.microservice.transactionservice.dto.DepositRequest;
import com.banking.microservice.transactionservice.dto.TransactionResponse;
import com.banking.microservice.transactionservice.dto.TransferRequest;
import com.banking.microservice.transactionservice.dto.WithdrawRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionResponse deposit(DepositRequest dto);

    TransactionResponse withdrw(WithdrawRequest dto);

    TransactionResponse transfer(TransferRequest dto);

    Page<TransactionResponse> getTransactionHistory(Long accountId, Pageable pageable);

    TransactionResponse getTransactionByReference(String referenceNumber);
}
