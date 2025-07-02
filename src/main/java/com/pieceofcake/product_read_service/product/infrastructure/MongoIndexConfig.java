package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.entity.ProductRead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoIndexConfig {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void createIndexes() {
        try {
            createProductIndexes();
            createFundingIndexes();
            createPieceIndexes();
            log.info("All MongoDB indexes created successfully");
        } catch (Exception e) {
            log.error("Failed to create MongoDB indexes", e);
        }
    }

    private void createProductIndexes() {
        IndexOperations indexOps = mongoTemplate.indexOps(ProductRead.class);
        
        // 1. 단일 필드 인덱스
        indexOps.ensureIndex(new Index().on("productUuid", Sort.Direction.ASC).unique());
        indexOps.ensureIndex(new Index().on("productStatus", Sort.Direction.ASC));
        indexOps.ensureIndex(new Index().on("createdAt", Sort.Direction.DESC));
        indexOps.ensureIndex(new Index().on("updatedAt", Sort.Direction.DESC));
        indexOps.ensureIndex(new Index().on("purchasePrice", Sort.Direction.ASC));
        indexOps.ensureIndex(new Index().on("aiEstimatedPrice", Sort.Direction.ASC));
        
        // 2. 복합 인덱스
        indexOps.ensureIndex(new Index()
            .on("mainCategory.categoryId", Sort.Direction.ASC)
            .on("subCategory.categoryId", Sort.Direction.ASC)
            .on("productStatus", Sort.Direction.ASC)
            .named("idx_category_status"));
        
        indexOps.ensureIndex(new Index()
            .on("productStatus", Sort.Direction.ASC)
            .on("createdAt", Sort.Direction.DESC)
            .named("idx_status_created"));
        
        indexOps.ensureIndex(new Index()
            .on("purchasePrice", Sort.Direction.ASC)
            .on("productStatus", Sort.Direction.ASC)
            .named("idx_price_status"));
        
        // 3. 텍스트 검색 인덱스
        indexOps.ensureIndex(new Index()
            .on("productName", Sort.Direction.ASC)
            .on("description", Sort.Direction.ASC)
            .on("storageLocation", Sort.Direction.ASC)
            .named("idx_text_search")
            .text());
        
        // 4. Geospatial 인덱스 (위치 기반 검색용)
        // indexOps.ensureIndex(new GeospatialIndex("location").named("idx_location"));
        
        // 5. TTL 인덱스 (30일 후 자동 삭제)
        indexOps.ensureIndex(new Index()
            .on("createdAt", Sort.Direction.ASC)
            .expire(2592000)  // 30일
            .named("idx_ttl_created"));
        
        // 6. Sparse 인덱스 (null 값 제외)
        indexOps.ensureIndex(new Index()
            .on("fundingRead.fundingUuid", Sort.Direction.ASC)
            .sparse()
            .named("idx_funding_uuid_sparse"));
        
        indexOps.ensureIndex(new Index()
            .on("pieceRead.pieceProductUuid", Sort.Direction.ASC)
            .sparse()
            .named("idx_piece_uuid_sparse"));
        
        log.info("Product indexes created successfully");
    }

    private void createFundingIndexes() {
        // Funding 관련 인덱스
        IndexOperations indexOps = mongoTemplate.indexOps("funding_read");
        
        indexOps.ensureIndex(new Index()
            .on("fundingUuid", Sort.Direction.ASC)
            .unique()
            .named("idx_funding_uuid"));
        
        indexOps.ensureIndex(new Index()
            .on("productUuid", Sort.Direction.ASC)
            .named("idx_funding_product"));
        
        indexOps.ensureIndex(new Index()
            .on("status", Sort.Direction.ASC)
            .on("createdAt", Sort.Direction.DESC)
            .named("idx_funding_status_created"));
        
        log.info("Funding indexes created successfully");
    }

    private void createPieceIndexes() {
        // Piece 관련 인덱스
        IndexOperations indexOps = mongoTemplate.indexOps("piece_read");
        
        indexOps.ensureIndex(new Index()
            .on("pieceProductUuid", Sort.Direction.ASC)
            .unique()
            .named("idx_piece_uuid"));
        
        indexOps.ensureIndex(new Index()
            .on("productUuid", Sort.Direction.ASC)
            .named("idx_piece_product"));
        
        indexOps.ensureIndex(new Index()
            .on("status", Sort.Direction.ASC)
            .on("createdAt", Sort.Direction.DESC)
            .named("idx_piece_status_created"));
        
        log.info("Piece indexes created successfully");
    }

    /**
     * 인덱스 성능 분석
     */
    public void analyzeIndexPerformance() {
        try {
            // 인덱스 사용 통계 조회
            mongoTemplate.executeCommand("{ collStats: 'product_read' }");
            
            // 인덱스 사용 현황 조회
            mongoTemplate.executeCommand("{ aggregate: 'product_read', pipeline: [{ $indexStats: {} }] }");
            
            log.info("Index performance analysis completed");
        } catch (Exception e) {
            log.error("Failed to analyze index performance", e);
        }
    }

    /**
     * 인덱스 최적화 (사용하지 않는 인덱스 제거)
     */
    public void optimizeIndexes() {
        try {
            // 사용하지 않는 인덱스 식별 및 제거
            // 실제 운영에서는 신중하게 진행해야 함
            
            log.info("Index optimization completed");
        } catch (Exception e) {
            log.error("Failed to optimize indexes", e);
        }
    }
} 