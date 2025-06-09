package com.pieceofcake.product_read_service.product.dto.in;

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
    private ProductStatus productStatus;
    private String storageLocation;
    private String description;
    private List<CreateProductImageEventDto> images;
    private Integer mainCategoryId;
    private Integer subCategoryId;

    @Builder
    public CreateProductEventDto(String productUuid, String productName, Long aiEstimatedPrice, Long purchasePrice, ProductStatus productStatus, String storageLocation,
                                 String description, List<CreateProductImageEventDto> images, Integer mainCategoryId, Integer subCategoryId) {
        this.productUuid = productUuid;
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }

    public static CreateProductEventDto from(ProductReadEvent productReadEvent) {
        return CreateProductEventDto.builder()
                .productUuid(productReadEvent.getProductUuid())
                .productName(productReadEvent.getProductName())
                .aiEstimatedPrice(productReadEvent.getAiEstimatedPrice())
                .purchasePrice(productReadEvent.getPurchasePrice())
                .productStatus(productReadEvent.getProductStatus())
                .storageLocation(productReadEvent.getStorageLocation())
                .description(productReadEvent.getDescription())
                .images(productReadEvent.getImages().stream().map(CreateProductImageEventDto::from).toList())
                .mainCategoryId(productReadEvent.getMainCategoryId())
                .subCategoryId(productReadEvent.getSubCategoryId())
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
                .mainCategoryId(mainCategoryId)
                .subCategoryId(subCategoryId)
                .build();
    }
}
