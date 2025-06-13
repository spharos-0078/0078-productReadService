package com.pieceofcake.product_read_service.kafka.controller;

import com.pieceofcake.product_read_service.kafka.event.CategoryNameEvent;
import com.pieceofcake.product_read_service.kafka.event.ProductReadEvent;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerController {

    private final ProductReadServiceImpl productReadService;
    private final ConcurrentHashMap<String, CompletableFuture<ProductReadEvent>> productEventFutureMap = new ConcurrentHashMap<>();

//    @KafkaListener(topics = "create-product-send-read", groupId = "product-read-group", containerFactory = "productReadEventListener")
//    public void consumeProductReadEvent(ProductReadEvent productReadEvent) {
//        log.info("get topic {}", productReadEvent);
//        productReadService.createProductRead(CreateProductEventDto.from(productReadEvent));
//    }

    // 1. product create 이벤트 수신
    @KafkaListener(topics = "create-product-send-read", groupId = "product-read-group", containerFactory = "productReadEventListener")
    public void consumeCreateProductReadEvent(ProductReadEvent productReadEvent) {
        log.info("Received product Create event: {}", productReadEvent);

        // 새로운 future 만들고 값 넣기
        CompletableFuture<ProductReadEvent> future = productEventFutureMap.computeIfAbsent(
                productReadEvent.getProductUuid(),
                productUuid -> new CompletableFuture<>()
        );

        // 기본 정보 완성 시도
        future.complete(productReadEvent);
    }

    @KafkaListener(topics = "update-product-send-read", groupId = "product-read-group", containerFactory = "productReadEventListener")
    public void consumeUpdateProductReadEvent(ProductReadEvent productReadEvent) {
        log.info("Received product Update event: {}", productReadEvent);

        // 새로운 future 만들고 값 넣기
        CompletableFuture<ProductReadEvent> future = productEventFutureMap.computeIfAbsent(
                productReadEvent.getProductUuid(),
                productUuid -> new CompletableFuture<>()
        );

        // 기본 정보 완성 시도
        future.complete(productReadEvent);
    }

    // 2. category name 이벤트 수신
    @KafkaListener(topics = "get-category-name-send-read", groupId = "product-read-group", containerFactory = "categoryNameReadEventListener")
    public void consumeCategoryNameEvent(CategoryNameEvent categoryNameEvent) {
        log.info("Received category name event: {}", categoryNameEvent);

        CompletableFuture<ProductReadEvent> future = productEventFutureMap.get(categoryNameEvent.getProductUuid());

        if (future == null) {
            log.warn("No future found for productId {}", categoryNameEvent.getProductUuid());
            return;
        }

        future.thenAccept(productReadEvent -> {
            if (productReadEvent.getEventType().equals("CREATE")) {
                productReadService.createProductRead(CreateProductEventDto.from(productReadEvent, categoryNameEvent));
            } else {
                productReadService.updateProductRead(CreateProductEventDto.from(productReadEvent, categoryNameEvent));
            }
//            productReadService.createProductRead(CreateProductEventDto.from(productReadEvent, categoryNameEvent));
            productEventFutureMap.remove(categoryNameEvent.getProductUuid());
        });
    }

    // 4. Delete 이벤트는 category 없이 단독 처리
    @KafkaListener(topics = "delete-product-send-read", groupId = "product-read-group", containerFactory = "productReadEventListener")
    public void consumeDeleteProductReadEvent(ProductReadEvent event) {
        log.info("Received DELETE event: {}", event);
        productReadService.deleteProductRead(event.getProductUuid());
    }
}

