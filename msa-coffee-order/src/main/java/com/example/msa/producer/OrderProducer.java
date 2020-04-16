package com.example.msa.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderProducer {

    @Value("${spring.kafka.order.topic")
    private String orderTopic;

    private final KafkaTemplate<String, OrderVO> kafkaTemplate;

    public void send(OrderVO vo) {
        ProducerRecord<String, OrderVO> producerRecord = new ProducerRecord<>(orderTopic, vo);
        kafkaTemplate.send(producerRecord);
        log.debug("==============> order sending kafka message ::: topic={}, value={}", orderTopic, vo);
    }
}
