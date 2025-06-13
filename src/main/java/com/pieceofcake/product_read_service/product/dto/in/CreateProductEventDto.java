package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.kafka.event.CategoryNameEvent;
import com.pieceofcake.product_read_service.kafka.event.ProductReadEvent;
import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateProductEventDto {
    private String productUuid;
    private String productName;
    private Long aiEstimatedPrice;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private String description;
    private List<CreateProductImageEventDto> images;
    private String mainCategoryName;
    private String subCategoryName;

    @Builder
    public CreateProductEventDto(String productUuid, String productName, Long aiEstimatedPrice, Long purchasePrice,
                                 String productStatus, String storageLocation, String description, List<CreateProductImageEventDto> images, String mainCategoryName, String subCategoryName) {
        this.productUuid = productUuid;
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategoryName = mainCategoryName;
        this.subCategoryName = subCategoryName;
    }

    public static CreateProductEventDto from(ProductReadEvent productReadEvent, CategoryNameEvent categoryNameEvent) {
        return CreateProductEventDto.builder()
                .productUuid(productReadEvent.getProductUuid())
                .productName(productReadEvent.getProductName())
                .aiEstimatedPrice(productReadEvent.getAiEstimatedPrice())
                .purchasePrice(productReadEvent.getPurchasePrice())
                .productStatus(productReadEvent.getProductStatus())
                .storageLocation(productReadEvent.getStorageLocation())
                .description(productReadEvent.getDescription())
                .images(productReadEvent.getImages().stream().map(CreateProductImageEventDto::from).toList())
                .mainCategoryName(categoryNameEvent.getMainCategoryName())
                .subCategoryName(categoryNameEvent.getSubCategoryName())
                .build();
    }

    public ProductReadMongoEntity toEntity() {
        return ProductReadMongoEntity.builder()
                .productUuid(productUuid)
                .productName(productName)
                .aiEstimatedPrice(aiEstimatedPrice)
                .purchasePrice(purchasePrice)
                .productStatus(productStatus)
                .storageLocation(storageLocation)
                .description(description)
                .images(images.stream().map(CreateProductImageEventDto::toEntity).toList())
                .mainCategoryName(mainCategoryName)
                .subCategoryName(subCategoryName)
                .build();
    }

    public ProductReadMongoEntity toEntity(ProductReadMongoEntity entity) {
        return ProductReadMongoEntity.builder()
                .id(entity.getId())
                .productUuid(productUuid)
                .productName(productName)
                .aiEstimatedPrice(aiEstimatedPrice)
                .purchasePrice(purchasePrice)
                .productStatus(productStatus)
                .storageLocation(storageLocation)
                .description(description)
                .images(images.stream().map(CreateProductImageEventDto::toEntity).toList())
                .mainCategoryName(mainCategoryName)
                .subCategoryName(subCategoryName)
                .build();
    }
}
