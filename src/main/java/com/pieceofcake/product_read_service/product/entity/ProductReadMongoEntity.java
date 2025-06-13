package com.pieceofcake.product_read_service.product.entity;

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
    private String mainCategoryName; // category 이름
    private String subCategoryName;

    @Builder
    public ProductReadMongoEntity(String id, String productName, String productUuid, Long aiEstimatedPrice,
                                  Long purchasePrice, String productStatus, String storageLocation, String description,
                                  List<ProductReadImageEntity> images, String mainCategoryName, String subCategoryName) {
        this.id = id;
        this.productName = productName;
        this.productUuid = productUuid;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategoryName = mainCategoryName;
        this.subCategoryName = subCategoryName;
    }
}
