package com.example.msa.consumer;

import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"${spring.kafka.order.topic}"})
@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"},
        classes = {OrderConsumerConfig.class, OrderConsumer.class})
class OrderConsumerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private EmbeddedKafkaBroker embeddedKafkaBroker;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired private OrderConsumer orderConsumer;

    @Test
    void listen() throws InterruptedException {

        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                .getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer,
                    embeddedKafkaBroker.getPartitionsPerTopic());
        }

        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        ProducerFactory<String, OrderVO> producerFactory = new DefaultKafkaProducerFactory<String, OrderVO>(producerProps, StringSerializer::new, JsonSerializer::new);
        KafkaTemplate<String, OrderVO> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic("orderTopic");

        OrderVO orderVO = OrderVO.builder()
                .orderNo(1)
                .coffeeName("Americano")
                .coffeeCount(1)
                .memberName("김석환").build();

        kafkaTemplate.sendDefault(orderVO);

    }
}