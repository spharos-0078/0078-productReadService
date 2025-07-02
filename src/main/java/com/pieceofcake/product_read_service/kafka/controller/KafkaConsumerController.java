package com.pieceofcake.product_read_service.kafka.controller;

import com.pieceofcake.product_read_service.common.transaction.TransactionManager;
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
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerController {

    private final ProductReadServiceImpl productReadService;
    private final FundingReadService fundingReadService;
    private final PieceReadServiceImpl pieceReadService;
    private final TransactionManager transactionManager;

    @KafkaListener(topics = "create-product", groupId = "create-product-read-group", containerFactory = "productReadEventListener")
    public void consumeCreateProductReadEvent(ProductReadEvent productReadEvent,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("create-product", messageKey);
        
        log.info("Received product Create event: {} with key: {}", productReadEvent, messageKey);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            productReadService.createProductRead(CreateProductEventDto.from(productReadEvent));
            
            // 보상 액션: 실패 시 삭제
            return () -> {
                log.info("Executing compensation for create-product: {}", productReadEvent.getProductUuid());
                productReadService.deleteProductRead(productReadEvent.getProductUuid());
            };
        });
    }

    @KafkaListener(topics = "update-product", groupId = "update-product-read-group", containerFactory = "productReadEventListener")
    public void consumeUpdateProductReadEvent(ProductReadEvent productReadEvent,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("update-product", messageKey);
        
        log.info("Received product Update event: {} with key: {}", productReadEvent, messageKey);
        
        // 업데이트 전 원본 데이터 백업
        ProductRead originalProduct = productReadService.getProductDetail(productReadEvent.getProductUuid()).toEntity();
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            productReadService.updateProductRead(UpdateProductEventDto.from(productReadEvent));
            
            // 보상 액션: 실패 시 원본으로 복원
            return () -> {
                log.info("Executing compensation for update-product: {}", productReadEvent.getProductUuid());
                productReadService.updateProductRead(UpdateProductEventDto.from(originalProduct));
            };
        });
    }

    @KafkaListener(topics = "delete-product", groupId = "delete-product-read-group", containerFactory = "productReadEventListener")
    public void consumeDeleteProductReadEvent(ProductReadEvent event,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("delete-product", messageKey);
        
        log.info("Received DELETE event: {} with key: {}", event, messageKey);
        
        // 삭제 전 원본 데이터 백업
        ProductRead originalProduct = productReadService.getProductDetail(event.getProductUuid()).toEntity();
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            productReadService.deleteProductRead(event.getProductUuid());
            
            // 보상 액션: 실패 시 원본 복원
            return () -> {
                log.info("Executing compensation for delete-product: {}", event.getProductUuid());
                productReadService.createProductRead(CreateProductEventDto.from(originalProduct));
            };
        });
    }

    @KafkaListener(topics = "create-funding", groupId = "create-funding-read-group", containerFactory = "fundingReadEventListener")
    public void consumeCreateFundingReadEvent(FundingReadEvent event,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("create-funding", messageKey);
        
        log.info("Received CREATE FUNDING event: {} with key: {}", event, messageKey);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            fundingReadService.createFundingRead(CreateFundingEventDto.from(event));
            
            // 보상 액션: 실패 시 삭제
            return () -> {
                log.info("Executing compensation for create-funding: {}", event.getProductUuid());
                fundingReadService.deleteFundingRead(event.getProductUuid());
            };
        });
    }

    @KafkaListener(topics = "delete-funding", groupId = "delete-funding-read-group", containerFactory = "fundingReadEventListener")
    public void consumeDeleteFundingReadEvent(FundingReadEvent event,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("delete-funding", messageKey);
        
        log.info("Received DELETE FUNDING event: {} with key: {}", event, messageKey);
        
        // 삭제 전 원본 데이터 백업
        // TODO: Funding 원본 데이터 백업
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            fundingReadService.deleteFundingRead(event.getProductUuid());
            
            // 보상 액션: 실패 시 원본 복원
            return () -> {
                log.info("Executing compensation for delete-funding: {}", event.getProductUuid());
                // TODO: 원본 데이터로 복원
            };
        });
    }

    @KafkaListener(topics = "remain-funding", groupId = "update-remain-funding-read-group", containerFactory = "fundingReadEventListener")
    public void consumeRemainFundingReadEvent(FundingReadEvent event,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("remain-funding", messageKey);
        
        log.info("Received REMAIN FUNDING event: {} with key: {}", event, messageKey);
        
        // 업데이트 전 원본 데이터 백업
        // TODO: Funding 원본 데이터 백업
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            fundingReadService.updateRemainPieces(UpdateRemainPiecesEventDto.from(event));
            
            // 보상 액션: 실패 시 원본 복원
            return () -> {
                log.info("Executing compensation for remain-funding: {}", event.getProductUuid());
                // TODO: 원본 데이터로 복원
            };
        });
    }

    @KafkaListener(topics = "create-piece-product", groupId = "create-piece-read-group", containerFactory = "pieceReadEventListener")
    public void consumeCreatePieceReadEvent(PieceReadEvent event,
                                          @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("create-piece", messageKey);
        
        log.info("Received CREATE PIECE PRODUCT event: {} with key: {}", event.getPieceProductUuid(), messageKey);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            pieceReadService.createPieceRead(CreatePieceEventDto.from(event));
            
            // 보상 액션: 실패 시 삭제
            return () -> {
                log.info("Executing compensation for create-piece: {}", event.getPieceProductUuid());
                // TODO: Piece 삭제 로직
            };
        });
    }

    @KafkaListener(topics = "daily-piece-trade", groupId = "update-batch-group", containerFactory = "batchReadEventListener")
    public void consumeCreatePieceReadEvent(BatchReadEvent event,
                                          @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("daily-piece-trade", messageKey);
        
        log.info("Received UPDATE PIECE BATCH event: {} with key: {}", event.getPieceProductUuid(), messageKey);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            pieceReadService.createPieceBatchRead(CreateBatchEventDto.from(event));
            
            // 보상 액션: 실패 시 롤백
            return () -> {
                log.info("Executing compensation for daily-piece-trade: {}", event.getPieceProductUuid());
                // TODO: 배치 처리 롤백 로직
            };
        });
    }

    @KafkaListener(topics = "vote-start", groupId = "vote-start-group", containerFactory = "stringEventListener")
    public void onVoteStart(String pieceProductUuid,
                          @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("vote-start", messageKey);
        
        log.info("Received vote-start event for voteUuid: {} with key: {}", pieceProductUuid, messageKey);
        
        // 상태 변경 전 원본 상태 백업
        PieceStatus originalStatus = pieceReadService.getPieceStatus(pieceProductUuid);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.VOTE);
            
            // 보상 액션: 실패 시 원본 상태로 복원
            return () -> {
                log.info("Executing compensation for vote-start: {}", pieceProductUuid);
                pieceReadService.updatePieceStatus(pieceProductUuid, originalStatus);
            };
        });
    }

    @KafkaListener(topics = "vote-close", groupId = "vote-close-group", containerFactory = "stringEventListener")
    public void onVoteClose(String pieceProductUuid,
                           @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("vote-close", messageKey);
        
        log.info("Received vote-close event for voteUuid: {} with key: {}", pieceProductUuid, messageKey);
        
        // 상태 변경 전 원본 상태 백업
        PieceStatus originalStatus = pieceReadService.getPieceStatus(pieceProductUuid);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.NONE);
            
            // 보상 액션: 실패 시 원본 상태로 복원
            return () -> {
                log.info("Executing compensation for vote-close: {}", pieceProductUuid);
                pieceReadService.updatePieceStatus(pieceProductUuid, originalStatus);
            };
        });
    }

    @KafkaListener(topics = "auction-start", groupId = "auction-start-group", containerFactory = "stringEventListener")
    public void onAuctionStart(String pieceProductUuid,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("auction-start", messageKey);
        
        log.info("Received auction-start event for voteUuid: {} with key: {}", pieceProductUuid, messageKey);
        
        // 상태 변경 전 원본 상태 백업
        PieceStatus originalStatus = pieceReadService.getPieceStatus(pieceProductUuid);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.AUCTION);
            
            // 보상 액션: 실패 시 원본 상태로 복원
            return () -> {
                log.info("Executing compensation for auction-start: {}", pieceProductUuid);
                pieceReadService.updatePieceStatus(pieceProductUuid, originalStatus);
            };
        });
    }

    @KafkaListener(topics = "auction-close", groupId = "auction-close-group", containerFactory = "stringEventListener")
    public void onAuctionClose(String pieceProductUuid,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
        String idempotencyKey = generateIdempotencyKey("auction-close", messageKey);
        
        log.info("Received auction-close event for auctionUuid: {} with key: {}", pieceProductUuid, messageKey);
        
        // 상태 변경 전 원본 상태 백업
        PieceStatus originalStatus = pieceReadService.getPieceStatus(pieceProductUuid);
        
        transactionManager.executeWithIdempotency(idempotencyKey, () -> {
            // 메인 액션
            pieceReadService.updatePieceStatus(pieceProductUuid, PieceStatus.NONE);
            
            // 보상 액션: 실패 시 원본 상태로 복원
            return () -> {
                log.info("Executing compensation for auction-close: {}", pieceProductUuid);
                pieceReadService.updatePieceStatus(pieceProductUuid, originalStatus);
            };
        });
    }

    /**
     * 멱등성 키 생성
     */
    private String generateIdempotencyKey(String operation, String messageKey) {
        return String.format("%s:%s:%s", operation, messageKey, UUID.randomUUID().toString());
    }
}