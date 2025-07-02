package com.pieceofcake.product_read_service.product.entity;

import com.pieceofcake.product_read_service.funding.entity.FundingRead;
import com.pieceofcake.product_read_service.piece.entity.PieceRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Document(collection = "product_read")
@CompoundIndexes({
    @CompoundIndex(name = "idx_category_status", def = "{'mainCategory.categoryId': 1, 'subCategory.categoryId': 1, 'productStatus': 1}"),
    @CompoundIndex(name = "idx_status_created", def = "{'productStatus': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_price_status", def = "{'purchasePrice': 1, 'productStatus': 1}"),
    @CompoundIndex(name = "idx_funding_uuid", def = "{'fundingRead.fundingUuid': 1}"),
    @CompoundIndex(name = "idx_piece_uuid", def = "{'pieceRead.pieceProductUuid': 1}")
})
public class ProductRead {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String productUuid;
    
    @TextIndexed(weight = 2)
    private String productName;
    
    @Indexed
    private Long aiEstimatedPrice;
    
    @TextIndexed(weight = 1)
    private String aiEstimatedDescription;
    
    @Indexed
    private Long purchasePrice;
    
    @Indexed
    private String productStatus;
    
    @TextIndexed(weight = 1)
    private String storageLocation;
    
    @TextIndexed(weight = 1)
    private String description;
    
    private List<ProductImageRead> images;
    private CategoryRead mainCategory;
    private CategoryRead subCategory;
    private FundingRead fundingRead;
    private PieceRead pieceRead;
    
    @Indexed
    private LocalDateTime createdAt;
    
    @Indexed
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

    public void updateProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}
