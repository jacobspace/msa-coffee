package com.example.msa.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class OrderProducer {

    @Value("${spring.kafka.order.topic}")
    private String orderTopic;

    private final KafkaTemplate<String, OrderVO> kafkaTemplate;

    public void send(OrderVO vo) {

        ProducerRecord<String, OrderVO> record = new ProducerRecord<>(orderTopic, vo);
        kafkaTemplate.send(record);
    }
}
