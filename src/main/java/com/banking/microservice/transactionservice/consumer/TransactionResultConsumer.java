package com.banking.microservice.transactionservice.consumer;

import com.banking.microservice.transactionservice.event.TransactionResultEvent;
import com.banking.microservice.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
@Slf4j
public class TransactionResultConsumer {
    private final TransactionService transacionService;

    @Value("${spring.transaction.topics.transaction-result}")
    private  String transactionResult;


    @KafkaListener(
            topics="${spring.transaction.topics.transaction-result}",
            groupId = "transaction-group"
    )
    public void consume(TransactionResultEvent event){
        log.info("Received Transactoin {} result.",event.getReferenceNumber());

        transacionService.transactionProcess(event);

    }


}
