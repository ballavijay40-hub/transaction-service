package com.banking.microservice.transactionservice.service;

import com.banking.microservice.transactionservice.dto.DepositRequest;
import com.banking.microservice.transactionservice.dto.TransactionResponse;
import com.banking.microservice.transactionservice.dto.TransferRequest;
import com.banking.microservice.transactionservice.dto.WithdrawRequest;
import com.banking.microservice.transactionservice.entity.Transaction;
import com.banking.microservice.transactionservice.enums.TransactionStatus;
import com.banking.microservice.transactionservice.enums.TransactionType;
import com.banking.microservice.transactionservice.event.TransactionRequestEvent;
import com.banking.microservice.transactionservice.event.TransactionResultEvent;
import com.banking.microservice.transactionservice.producer.TransactionProducer;
import com.banking.microservice.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    private  final TransactionProducer transactionProducer;

    @Override
    public  TransactionResponse deposit(DepositRequest dto){

        //transaction ready cheyali
        Transaction transaction=buildDepositTransaction(dto);

        //saving the transaction
        transactionRepository.save(transaction);

        //building teh event
        TransactionRequestEvent event=buildEvent(transaction);

        //publish the event
        transactionProducer.publish(event);

        //return response
        return mapToResponse(transaction);



    }


    @Override
    public TransactionResponse withdrw(WithdrawRequest dto){
        Transaction transaction=buildWithdrawTransaction(dto);

        transactionRepository.save(transaction);

        TransactionRequestEvent event=buildEvent(transaction);

        transactionProducer.publish(event);

        return mapToResponse(transaction);
    }


    @Override
    public TransactionResponse transfer(TransferRequest dto){
        Transaction transaction=buildTransferTransaction(dto);

        transactionRepository.save(transaction);

        TransactionRequestEvent event=buildEvent(transaction);

        transactionProducer.publish(event);

        return mapToResponse(transaction);

    }

    @Override
    public TransactionResponse getTransactionByReference(String referenceNumber){
        Transaction transaction=transactionRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(()->new RuntimeException("reference number not found."));

        return mapToResponse(transaction);

    }

    @Override
    public Page<TransactionResponse> getTransactionHistory(Long accountId, Pageable pageable){
        return  transactionRepository.findByFromAccountIdOrToAccountId(accountId,accountId,pageable)
                .map(this::mapToResponse);
    }



    @Override
    public void transactionProcess(TransactionResultEvent transactionResultEvent){
        Transaction transaction=transactionRepository.findByReferenceNumber(transactionResultEvent.getReferenceNumber())
                .orElseThrow(()->new RuntimeException("transaction not found "+transactionResultEvent.getReferenceNumber()));

        if(transaction.getStatus()!=TransactionStatus.PENDING){
            log.warn("Transaction {} already processed.",transaction.getReferenceNumber());
            return;

        }

        transaction.setStatus(transactionResultEvent.getStatus());
        transaction.setFailureReason(transactionResultEvent.getFailureReason());

        transactionRepository.save(transaction);

        log.info("transaction {} updated to {}.",transaction.getReferenceNumber(),transaction.getStatus());
    }

















//these are helper methods

    private String generateReferenceNumber(){
        return "TXN-"+ UUID.randomUUID()
                .toString()
                .replace("-","")
                .substring(0,12)
                .toUpperCase();
    }

    private Transaction buildTransferTransaction(TransferRequest request){
        return Transaction.builder()
                .referenceNumber(generateReferenceNumber())
                .fromAccountId(request.getFromAccountId())
                .toAccountId(request.getToAccountId())
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .description(request.getDescription())
                .build();
    }


    private Transaction buildDepositTransaction(DepositRequest request){
        return   Transaction.builder()
                .referenceNumber(generateReferenceNumber())
                .toAccountId(request.getAccountId())
                .amount(request.getAmount())
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.PENDING)
                .description(request.getDescription())
                .build();

    }

    private Transaction buildWithdrawTransaction(WithdrawRequest request){
        return Transaction.builder()
                .referenceNumber(generateReferenceNumber())
                .fromAccountId(request.getAccountId())
                .amount(request.getAmount())
                .type(TransactionType.WITHDRAW)
                .status(TransactionStatus.PENDING)
                .description(request.getDescription())
                .build();
    }

    private TransactionRequestEvent buildEvent(Transaction transaction){
        return TransactionRequestEvent.builder()
                .referenceNumber(transaction.getReferenceNumber())
                .fromAccountId(transaction.getFromAccountId())
                .toAccountId(transaction.getToAccountId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getType())
                .build();
    }

    private TransactionResponse mapToResponse(Transaction transaction){
        return  TransactionResponse.builder()
                .id(transaction.getId())
                .referenceNumber(transaction.getReferenceNumber())
                .fromAccountId(transaction.getFromAccountId())
                .toAccountId(transaction.getToAccountId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getType())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .failureReason(transaction.getFailureReason())
                .build();
    }


}
