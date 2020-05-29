package com.example.msa.consumer;

import com.example.msa.repository.Status;
import com.example.msa.repository.StatusRepository;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(partitions = 1, topics = {"${spring.kafka.order.topic}"})
@SpringBootTest(
        classes = {OrderConsumerConfig.class, OrderConsumer.class},
        properties = {
                "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
                "spring.kafka.consumer.auto-offset-reset=earliest"})
class OrderConsumerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private EmbeddedKafkaBroker embeddedKafkaBroker;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired private OrderConsumer orderConsumer;
    @MockBean private StatusRepository statusRepository;

    @Test
    void listen() throws InterruptedException {

        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                .getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer,
                    embeddedKafkaBroker.getPartitionsPerTopic());
        }

        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        ProducerFactory<String, OrderVO> producerFactory = new DefaultKafkaProducerFactory<String, OrderVO>(producerProps, new StringSerializer(), new JsonSerializer<>());
        KafkaTemplate<String, OrderVO> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic("orderTopic");

        OrderVO orderVO = OrderVO.builder()
                .orderNo(1)
                .coffeeName("Americano")
                .coffeeCount(1)
                .memberName("김석환").build();

        kafkaTemplate.sendDefault(orderVO);

        Thread.sleep(500);

        Mockito.verify(statusRepository).save(ArgumentMatchers.any(Status.class));
    }
}