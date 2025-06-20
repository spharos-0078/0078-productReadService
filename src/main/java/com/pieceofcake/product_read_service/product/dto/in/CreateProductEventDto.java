package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.kafka.event.ProductReadEvent;
import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
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
    private String aiEstimatedDescription;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private String description;
    private List<CreateProductImageEventDto> images;
    private CreateCategoryEventDto mainCategory;
    private CreateCategoryEventDto subCategory;

    @Builder
    public CreateProductEventDto(String productUuid, String productName, Long aiEstimatedPrice, String aiEstimatedDescription,
                                 Long purchasePrice, String productStatus, String storageLocation, String description, List<CreateProductImageEventDto> images, CreateCategoryEventDto mainCategory, CreateCategoryEventDto subCategory) {
        this.productUuid = productUuid;
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public static CreateProductEventDto from(ProductReadEvent productReadEvent) {
        return CreateProductEventDto.builder()
                .productUuid(productReadEvent.getProductUuid())
                .productName(productReadEvent.getProductName())
                .aiEstimatedPrice(productReadEvent.getAiEstimatedPrice())
                .aiEstimatedDescription(productReadEvent.getAiEstimatedDescription())
                .purchasePrice(productReadEvent.getPurchasePrice())
                .productStatus(productReadEvent.getProductStatus())
                .storageLocation(productReadEvent.getStorageLocation())
                .description(productReadEvent.getDescription())
                .images(productReadEvent.getImages().stream().map(CreateProductImageEventDto::from).toList())
                .mainCategory(CreateCategoryEventDto.from(productReadEvent.getMainCategory()))
                .subCategory(CreateCategoryEventDto.from(productReadEvent.getSubCategory()))
                .build();
    }

    public ProductReadMongoEntity toEntity() {
        return ProductReadMongoEntity.builder()
                .productUuid(productUuid)
                .productName(productName)
                .aiEstimatedPrice(aiEstimatedPrice)
                .aiEstimatedDescription(aiEstimatedDescription)
                .purchasePrice(purchasePrice)
                .productStatus(productStatus)
                .storageLocation(storageLocation)
                .description(description)
                .images(images.stream().map(CreateProductImageEventDto::toEntity).toList())
                .mainCategory(mainCategory.toEntity())
                .subCategory(subCategory.toEntity())
                .build();
    }
}
