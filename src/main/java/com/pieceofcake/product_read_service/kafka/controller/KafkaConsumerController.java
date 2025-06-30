package com.pieceofcake.product_read_service.kafka.controller;

import com.pieceofcake.product_read_service.funding.application.FundingReadService;
import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;
import com.pieceofcake.product_read_service.funding.dto.in.UpdateRemainPiecesEventDto;
import com.pieceofcake.product_read_service.kafka.event.*;
import com.pieceofcake.product_read_service.piece.application.PieceReadServiceImpl;
import com.pieceofcake.product_read_service.piece.dto.in.CreateBatchEventDto;
import com.pieceofcake.product_read_service.piece.dto.in.CreatePieceEventDto;
import com.pieceofcake.product_read_service.piece.entity.PieceStatus;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductStatusEventDto;
import com.pieceofcake.product_read_service.product.entity.ProductStatus;
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
    private final PieceReadServiceImpl pieceReadService;

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
        log.info("Received DELETE FUNDING event: {}", event);
        fundingReadService.deleteFundingRead(event.getProductUuid());
    }

    @KafkaListener(topics = "remain-funding", groupId = "update-remain-funding-read-group", containerFactory = "fundingReadEventListener")
    public void consumeRemainFundingReadEvent(FundingReadEvent event) {
        log.info("Received REMAIN FUNDING event: {}", event);
        fundingReadService.updateRemainPieces(UpdateRemainPiecesEventDto.from(event));
    }

    @KafkaListener(topics = "create-piece-product", groupId = "create-piece-read-group", containerFactory = "pieceReadEventListener")
    public void consumeCreatePieceReadEvent(PieceReadEvent event) {
        log.info("Received CREATE PIECE PRODUCT event: {}", event.getPieceProductUuid());
        pieceReadService.createPieceRead(CreatePieceEventDto.from(event));
    }

    @KafkaListener(topics = "daily-piece-trade", groupId = "update-batch-group", containerFactory = "batchReadEventListener")
    public void consumeCreatePieceReadEvent(BatchReadEvent event) {
        log.info("Received UPDATE PIECE BATCH event: {}", event.getPieceProductUuid());
        pieceReadService.createPieceBatchRead(CreateBatchEventDto.from(event));
    }

    @KafkaListener(topics = "vote-start", groupId = "vote-start-group", containerFactory = "stringEventListener")
    public void onVoteStart(String pieceProductUuid) {
        // TODO: 메시지 처리 로직 (예: Vote 상태 업데이트, 알림 발송 등)
        System.out.println("Received vote-start event for voteUuid=@@@@@@@" + pieceProductUuid);
        pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.VOTE);
    }

    @KafkaListener(topics = "vote-close", groupId = "vote-close-group", containerFactory = "stringEventListener")
    public void onVoteClose(String pieceProductUuid) {
        // TODO: 메시지 처리 로직 (예: Vote 상태 업데이트, 알림 발송 등)
        System.out.println("Received vote-close event for voteUuid=@@@@@@@" + pieceProductUuid);
        pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.NONE);
    }

    @KafkaListener(topics = "auction-start", groupId = "auction-start-group", containerFactory = "stringEventListener")
    public void onAuctionStart(String pieceProductUuid) {
        // TODO: 메시지 처리 로직 (예: Vote 상태 업데이트, 알림 발송 등)
        System.out.println("Received auction-start event for voteUuid=@@@@@@@" + pieceProductUuid);
        pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.AUCTION);
    }

    @KafkaListener(topics = "auction-close", groupId = "auction-close-group", containerFactory = "stringEventListener")
    public void onAuctionClose(String pieceProductUuid) {
        System.out.println("Received auction-close event for auctionUuid=@@@@@@@" + pieceProductUuid);
        pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.NONE);
    }

    @KafkaListener(topics = "update-product-status", groupId = "product-status-read-group", containerFactory = "productStatusEventListener")
    public void updateProductStatus(ProductStatusEvent productStatusEvent) {
        System.out.println("Received product-status event for productUuid=@@@@@@@" + productStatusEvent.getProductUuid());
        productReadService.updateProductStatus(UpdateProductStatusEventDto.from(productStatusEvent));
    }
}