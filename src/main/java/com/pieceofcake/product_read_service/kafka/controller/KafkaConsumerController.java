package com.pieceofcake.product_read_service.kafka.controller;

import com.pieceofcake.product_read_service.kafka.event.ProductReadEvent;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerController {

    private final ProductReadServiceImpl productReadService;
//    private final ConcurrentHashMap<String, CompletableFuture<ProductReadEvent>> productEventFutureMap = new ConcurrentHashMap<>();

    @KafkaListener(topics = "create-product-send-read", groupId = "product-read-group", containerFactory = "productReadEventListener")
    public void consumeProductReadEvent(ProductReadEvent productReadEvent) {
        log.info("get topic {}", productReadEvent);
        productReadService.createProductRead(CreateProductEventDto.from(productReadEvent));
    }
}

