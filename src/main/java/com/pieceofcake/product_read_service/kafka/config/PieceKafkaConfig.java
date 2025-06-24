package com.pieceofcake.product_read_service.kafka.config;

import com.pieceofcake.product_read_service.kafka.event.PieceReadEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class PieceKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> pieceConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "piece-read-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, PieceReadEvent.class);

        return props;
    }

    @Bean
    public ConsumerFactory<String, PieceReadEvent> pieceReadEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                pieceConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(PieceReadEvent.class, false))
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PieceReadEvent> pieceReadEventListener() {
        ConcurrentKafkaListenerContainerFactory<String, PieceReadEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pieceReadEventConsumerFactory());
        return factory;
    }
}
