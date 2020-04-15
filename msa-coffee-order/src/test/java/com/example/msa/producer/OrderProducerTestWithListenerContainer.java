package com.example.msa.producer;

import com.example.msa.repository.Order;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.hamcrest.KafkaMatchers;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"${spring.kafka.order.topic}"})
@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"},
                classes = {OrderProducerConfig.class, OrderProducer.class})
class OrderProducerTestWithListenerContainer {

    @Autowired private OrderProducer orderProducer;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void send() throws InterruptedException {

        final KafkaMessageListenerContainer<String, OrderVO> container = buildConsumer(StringDeserializer.class, JsonDeserializer.class);
        final BlockingQueue<ConsumerRecord<String, OrderVO>> records = new LinkedBlockingQueue<>();

        container.setupMessageListener(new MessageListener<String, OrderVO>() {
            @Override
            public void onMessage(ConsumerRecord<String, OrderVO> record) {
                records.add(record);
            }
        });

        container.setBeanName("orderProducerTests");
        container.start();
        ContainerTestUtils.waitForAssignment(container,
                embeddedKafkaBroker.getPartitionsPerTopic());

        Order order = Order.builder()
                .orderNo(1)
                .coffeeName("Americano")
                .coffeeCount(1)
                .memberName("김석환").build();

        OrderVO orderVO = new OrderVO(order);

        orderProducer.send(orderVO);

        ConsumerRecord<String, OrderVO> record = records.poll(5, TimeUnit.SECONDS);

        MatcherAssert.assertThat(record, KafkaMatchers.hasKey(null));
        MatcherAssert.assertThat(record, KafkaMatchers.hasValue(orderVO));

        container.stop();

    }

    private <K, V> KafkaMessageListenerContainer<K, V> buildConsumer(Class<? extends Deserializer> keyDeserializer, Class<? extends Deserializer> valueDeserializer) {

        final Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testMetricsEncodedAsSent", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer.getName());
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        final DefaultKafkaConsumerFactory<K, V> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        ContainerProperties containerProperties = new ContainerProperties("orderTopic");
        KafkaMessageListenerContainer<K, V> container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        return container;
    }

}