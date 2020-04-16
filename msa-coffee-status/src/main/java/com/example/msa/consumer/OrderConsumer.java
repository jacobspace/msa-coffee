package com.example.msa.consumer;

import com.example.msa.repository.Status;
import com.example.msa.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    private final StatusRepository statusRepository;

    @KafkaListener(
            groupId = "order-group-01",
            topics = "${spring.kafka.order.topic}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, OrderVO> record) {
        OrderVO orderVO = record.value();
        LOGGER.debug("Received order message: " + orderVO);
        statusRepository.Save(Status.builder().orderHistory(orderVO.toString()).build());
    }
}
