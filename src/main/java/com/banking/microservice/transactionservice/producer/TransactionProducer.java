package com.banking.microservice.transactionservice.producer;


import com.banking.microservice.transactionservice.event.TransactionRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionProducer {

    private  final KafkaTemplate<String, TransactionRequestEvent> kafkaTemplate;

    @Value("${spring.kafka.topics.transaction-request}")
    private String transactionRequestTopic;

    public  void publish(TransactionRequestEvent event){
        kafkaTemplate.send(transactionRequestTopic,event.getReferenceNumber(),event);
        log.info("published transaction {} to kafka",event.getReferenceNumber());
    }




}
