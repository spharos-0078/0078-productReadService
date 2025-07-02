package com.pieceofcake.product_read_service.kafka.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class DeadLetterQueueConfig {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {
        return new DeadLetterPublishingRecoverer(kafkaTemplate, (record, ex) -> {
            // 데드레터 큐 토픽 이름 생성
            String originalTopic = record.topic();
            String dlqTopic = originalTopic + ".DLQ";
            
            log.error("Message sent to DLQ: {} -> {}", originalTopic, dlqTopic);
            log.error("Error: {}", ex.getMessage());
            
            return dlqTopic;
        });
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {
        // 3번 재시도 후 데드레터 큐로 전송
        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3L);
        
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            deadLetterPublishingRecoverer(), 
            fixedBackOff
        );
        
        // 특정 예외는 즉시 데드레터 큐로 전송
        errorHandler.addNotRetryableExceptions(
            IllegalArgumentException.class,
            NullPointerException.class
        );
        
        return errorHandler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setCommonErrorHandler(defaultErrorHandler());
        factory.setConsumerFactory(consumerFactory());
        
        return factory;
    }

    private ConsumerFactory<String, Object> consumerFactory() {
        // 기본 ConsumerFactory 설정
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private java.util.Map<String, Object> consumerConfigs() {
        java.util.Map<String, Object> props = new java.util.HashMap<>();
        // 기본 설정들...
        return props;
    }
} 