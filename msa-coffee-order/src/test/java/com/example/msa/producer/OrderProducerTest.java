package com.example.msa.producer;

import com.example.msa.repository.Order;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hamcrest.MatcherAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.hamcrest.KafkaMatchers;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"${spring.kafka.order.topic}"})
@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"},
                classes = {OrderProducerConfig.class, OrderProducer.class})
class OrderProducerTest {

    @Autowired private OrderProducer orderProducer;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void send() {

        final Consumer<String, OrderVO> consumer = buildConsumer(StringDeserializer.class, JsonDeserializer.class);
//        final Consumer<String, OrderVO> consumer = buildConsumerOrderVO();

        Order order = Order.builder()
                .orderNo(1)
                .coffeeName("Americano")
                .coffeeCount(1)
                .memberName("김석환").build();

        OrderVO orderVO = new OrderVO(order);

        orderProducer.send(orderVO);

        embeddedKafkaBroker.consumeFromEmbeddedTopics(consumer, "orderTopic");
        final ConsumerRecord<String, OrderVO> record = KafkaTestUtils.getSingleRecord(consumer, "orderTopic", 500);

        MatcherAssert.assertThat(record, KafkaMatchers.hasKey(null));
        MatcherAssert.assertThat(record, KafkaMatchers.hasValue(orderVO));

    }

    private <K, V> Consumer<K, V> buildConsumer(Class<? extends Deserializer> keyDeserializer, Class<? extends Deserializer> valueDeserializer) {

        final Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testMetricsEncodedAsSent", "true", embeddedKafkaBroker);

        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer.getName());
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        final DefaultKafkaConsumerFactory<K, V> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        return consumerFactory.createConsumer();
    }

    private Consumer<String, OrderVO> buildConsumerOrderVO() {

        final Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testMetricsEncodedAsSent", "true", embeddedKafkaBroker);

        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        JsonDeserializer<OrderVO> jsonDeserializer = new JsonDeserializer<OrderVO>(OrderVO.class);

        final DefaultKafkaConsumerFactory<String, OrderVO> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), jsonDeserializer);

        return consumerFactory.createConsumer();
    }

}