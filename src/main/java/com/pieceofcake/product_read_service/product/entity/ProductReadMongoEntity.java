package com.pieceofcake.product_read_service.product.entity;

import com.pieceofcake.product_read_service.funding.entity.FundingRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@Document(collection = "product_read")
public class ProductReadMongoEntity {
    @Id
    private String id;
    private String productName;
    private String productUuid;
    private Long aiEstimatedPrice;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private String description;
    private List<ProductReadImageEntity> images;
    private CategoryReadEntity mainCategory;
    private CategoryReadEntity subCategory;
    private FundingRead fundingRead;

    @Builder
    public ProductReadMongoEntity(String id, String productName, String productUuid, Long aiEstimatedPrice, Long purchasePrice,
                                  String productStatus, String storageLocation, String description, List<ProductReadImageEntity> images,
                                  CategoryReadEntity mainCategory, CategoryReadEntity subCategory, FundingRead fundingRead) {
        this.id = id;
        this.productName = productName;
        this.productUuid = productUuid;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.fundingRead = fundingRead;
    }

    public void createFundingRead(FundingRead fundingRead) {
        this.fundingRead = fundingRead;
    }
}
