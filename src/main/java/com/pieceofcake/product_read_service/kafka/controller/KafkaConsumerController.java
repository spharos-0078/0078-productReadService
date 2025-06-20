package com.pieceofcake.product_read_service.kafka.controller;

import com.pieceofcake.product_read_service.funding.application.FundingReadService;
import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;
import com.pieceofcake.product_read_service.kafka.event.FundingReadEvent;
import com.pieceofcake.product_read_service.kafka.event.ProductReadEvent;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerController {

    private final ProductReadServiceImpl productReadService;
    private final FundingReadService fundingReadService;

    @KafkaListener(topics = "create-product", groupId = "create-product-read-group", containerFactory = "productReadEventListener")
    public void consumeCreateProductReadEvent(ProductReadEvent productReadEvent) {
        log.info("Received product Create event: {}", productReadEvent);
        productReadService.createProductRead(CreateProductEventDto.from(productReadEvent));
    }

    @KafkaListener(topics = "update-product", groupId = "update-product-read-group", containerFactory = "productReadEventListener")
    public void consumeUpdateProductReadEvent(ProductReadEvent productReadEvent) {
        log.info("Received product Update event: {}", productReadEvent);
        productReadService.updateProductRead(UpdateProductEventDto.from(productReadEvent));
    }


    @KafkaListener(topics = "delete-product", groupId = "delete-product-read-group", containerFactory = "productReadEventListener")
    public void consumeDeleteProductReadEvent(ProductReadEvent event) {
        log.info("Received DELETE event: {}", event);
        productReadService.deleteProductRead(event.getProductUuid());
    }

    @KafkaListener(topics = "create-funding", groupId = "create-funding-read-group", containerFactory = "fundingReadEventListener")
    public void consumeCreateFundingReadEvent(FundingReadEvent event) {
        log.info("Received CREATE FUNDING event: {}", event);
        fundingReadService.createFundingRead(CreateFundingEventDto.from(event));
    }

    @KafkaListener(topics = "delete-funding", groupId = "delete-funding-read-group", containerFactory = "fundingReadEventListener")
    public void consumeDeleteFundingReadEvent(FundingReadEvent event) {
        log.info("Received DELETE FUNDING PRODUCT-UUID: {}", event.getProductUuid());
        fundingReadService.deleteFundingRead(event.getProductUuid());
    }
}