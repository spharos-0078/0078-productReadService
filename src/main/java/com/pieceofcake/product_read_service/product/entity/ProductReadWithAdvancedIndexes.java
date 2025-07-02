package com.pieceofcake.product_read_service.product.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "product_read_advanced")
@CompoundIndexes({
    // 복합 인덱스 - 카테고리 + 상태 + 생성일
    @CompoundIndex(
        name = "idx_category_status_created",
        def = "{'mainCategory.categoryId': 1, 'subCategory.categoryId': 1, 'productStatus': 1, 'createdAt': -1}",
        background = true,
        sparse = false
    ),
    
    // 복합 인덱스 - 가격 범위 + 상태
    @CompoundIndex(
        name = "idx_price_range_status",
        def = "{'purchasePrice': 1, 'aiEstimatedPrice': 1, 'productStatus': 1}",
        background = true
    ),
    
    // 복합 인덱스 - 상태 + 업데이트일 (최신순)
    @CompoundIndex(
        name = "idx_status_updated_desc",
        def = "{'productStatus': 1, 'updatedAt': -1}",
        background = true
    ),
    
    // 복합 인덱스 - Funding 관련
    @CompoundIndex(
        name = "idx_funding_status",
        def = "{'fundingRead.fundingUuid': 1, 'productStatus': 1}",
        background = true,
        sparse = true  // null 값 제외
    ),
    
    // 복합 인덱스 - Piece 관련
    @CompoundIndex(
        name = "idx_piece_status",
        def = "{'pieceRead.pieceProductUuid': 1, 'productStatus': 1}",
        background = true,
        sparse = true
    ),
    
    // TTL 인덱스 - 30일 후 자동 삭제
    @CompoundIndex(
        name = "idx_ttl_created",
        def = "{'createdAt': 1}"
    )
})
public class ProductReadWithAdvancedIndexes {
    
    @Id
    private String id;
    
    // 고유 인덱스
    @Indexed(unique = true, background = true)
    private String productUuid;
    
    // 텍스트 검색 인덱스 (가중치 3)
    @TextIndexed(weight = 3)
    private String productName;
    
    // 일반 인덱스
    @Indexed(background = true)
    private Long aiEstimatedPrice;
    
    // 텍스트 검색 인덱스 (가중치 1)
    @TextIndexed(weight = 1)
    private String aiEstimatedDescription;
    
    // 일반 인덱스
    @Indexed(background = true)
    private Long purchasePrice;
    
    // 일반 인덱스
    @Indexed(background = true)
    private String productStatus;
    
    // 텍스트 검색 인덱스 (가중치 1)
    @TextIndexed(weight = 1)
    private String storageLocation;
    
    // 텍스트 검색 인덱스 (가중치 1)
    @TextIndexed(weight = 1)
    private String description;
    
    // 날짜 인덱스
    @Indexed(background = true)
    private LocalDateTime createdAt;
    
    // 날짜 인덱스
    @Indexed(background = true)
    private LocalDateTime updatedAt;
    
    // 기타 필드들...
} 