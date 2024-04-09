package ru.heumn.orderservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.heumn.orderservice.storages.dto.OrderDtoRequest;
import ru.heumn.orderservice.storages.event.OrderCreatedEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    String servers;

    @Value("${spring.kafka.producer.key-serializer}")
    String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    String acks;

    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    String timeout;

    @Value("${spring.kafka.producer.properties.linger.ms}")
    String linger;

    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    String requestTimeout;

    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    String idempotence;

    @Value("${spring.kafka.producer.properties.max.in.flight.request.per.connection}")
    String maxInFlightRequest;


    Map<String, Object> configProducer() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, timeout);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequest);
        return config;
    }

//    @Bean
//    @Qualifier(value = "producerFactory2")
//    ProducerFactory<String, OrderDtoRequest> producerFactory2() {
//        return new DefaultKafkaProducerFactory<>(configProducer());
//    }
//
//    @Bean
//    @Qualifier(value = "kafkaTemplate2")
//    KafkaTemplate<String, OrderDtoRequest> kafkaTemplate2() {
//        return new KafkaTemplate<String, OrderDtoRequest>(producerFactory2());
//    }

    @Bean
    @Qualifier(value = "producerFactory1")
    ProducerFactory<String, OrderCreatedEvent> producerFactory1() {
        return new DefaultKafkaProducerFactory<>(configProducer());
    }

    @Bean
    @Qualifier(value = "kafkaTemplate1")
    KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate1() {
        return new KafkaTemplate<String, OrderCreatedEvent>(producerFactory1());
    }


    @Bean
    NewTopic createTopic() {
        return TopicBuilder.name("orders-to-client-events-topic")
                .configs(Map.of("min.insync.replicas", "2"))
                .replicas(3)
                .partitions(3)
                .build();
    }
}
