package com.pieceofcake.product_read_service.product.entity;

import com.pieceofcake.product_read_service.funding.entity.FundingRead;
import com.pieceofcake.product_read_service.piece.entity.PieceRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Document(collection = "product_read")
public class ProductRead {
    @Id
    private String id;
    private String productName;
    private String productUuid;
    private Long aiEstimatedPrice;
    private String aiEstimatedDescription;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private String description;
    private List<ProductImageRead> images;
    private CategoryRead mainCategory;
    private CategoryRead subCategory;
    private FundingRead fundingRead;
    private PieceRead pieceRead;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ProductRead(String id, String productName, String productUuid, Long aiEstimatedPrice, String aiEstimatedDescription,
                       Long purchasePrice, String productStatus, String storageLocation, String description,
                       List<ProductImageRead> images, CategoryRead mainCategory, CategoryRead subCategory,
                       FundingRead fundingRead, PieceRead pieceRead, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productName = productName;
        this.productUuid = productUuid;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.fundingRead = fundingRead;
        this.pieceRead = pieceRead;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void createFundingRead(FundingRead fundingRead) {
        this.fundingRead = fundingRead;
    }

    public void createPieceRead(PieceRead pieceRead) {
        this.pieceRead = pieceRead;
    }
}
