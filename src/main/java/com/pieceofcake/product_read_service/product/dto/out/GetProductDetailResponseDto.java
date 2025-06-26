package com.pieceofcake.product_read_service.product.dto.out;

import com.pieceofcake.product_read_service.product.entity.ProductRead;
import com.pieceofcake.product_read_service.product.vo.out.GetProductDetailResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProductDetailResponseDto {
    private String productName;
    private Long aiEstimatedPrice;
    private String aiEstimatedDescription;
    private String description;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private List<GetProductImageResponseDto> images;
    private GetCategoryResponseDto mainCategory;
    private GetCategoryResponseDto subCategory;

    @Builder
    public GetProductDetailResponseDto(String productName, Long aiEstimatedPrice, String aiEstimatedDescription, String description,
                                       Long purchasePrice, String productStatus, String storageLocation, List<GetProductImageResponseDto> images,
                                       GetCategoryResponseDto mainCategory, GetCategoryResponseDto subCategory) {
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public static GetProductDetailResponseDto from(ProductRead productRead) {
        return GetProductDetailResponseDto.builder()
                .productName(productRead.getProductName())
                .aiEstimatedPrice(productRead.getAiEstimatedPrice())
                .aiEstimatedDescription(productRead.getAiEstimatedDescription())
                .productStatus(productRead.getProductStatus())
                .purchasePrice(productRead.getPurchasePrice())
                .storageLocation(productRead.getStorageLocation())
                .description(productRead.getDescription())
                .images(productRead.getImages().stream().map(GetProductImageResponseDto::from).toList())
                .mainCategory(GetCategoryResponseDto.from(productRead.getMainCategory()))
                .subCategory(GetCategoryResponseDto.from(productRead.getSubCategory()))
                .build();
    }

    public GetProductDetailResponseVo toVo() {
        return GetProductDetailResponseVo.builder()
                .productName(productName)
                .aiEstimatedPrice(aiEstimatedPrice)
                .aiEstimatedDescription(aiEstimatedDescription)
                .productStatus(productStatus)
                .purchasePrice(purchasePrice)
                .storageLocation(storageLocation)
                .description(description)
                .images(images.stream().map(GetProductImageResponseDto::toVo).toList())
                .mainCategory(mainCategory.toVo())
                .subCategory(subCategory.toVo())
                .build();
    }
}
