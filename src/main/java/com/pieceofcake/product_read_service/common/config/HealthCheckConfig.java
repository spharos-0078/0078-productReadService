package com.pieceofcake.product_read_service.common.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckConfig implements HealthIndicator {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public HealthCheckConfig(MongoTemplate mongoTemplate, KafkaTemplate<String, Object> kafkaTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Health health() {
        try {
            // MongoDB 연결 확인
            mongoTemplate.getDb().runCommand("{ ping: 1 }");
            
            // Kafka 연결 확인
            kafkaTemplate.getDefaultTopic();
            
            return Health.up()
                    .withDetail("mongodb", "UP")
                    .withDetail("kafka", "UP")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
} 