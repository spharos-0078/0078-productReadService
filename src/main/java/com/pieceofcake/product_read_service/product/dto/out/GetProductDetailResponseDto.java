package com.pieceofcake.product_read_service.product.dto.out;

import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import com.pieceofcake.product_read_service.product.vo.out.GetCategoryResponseVo;
import com.pieceofcake.product_read_service.product.vo.out.GetProductDetailResponseVo;
import com.pieceofcake.product_read_service.product.vo.out.GetProductImageResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProductDetailResponseDto {
    private String productName;
    private Long aiEstimatedPrice;
    private String description;
    private List<GetProductImageResponseDto> images;
    private GetCategoryResponseDto mainCategory;
    private GetCategoryResponseDto subCategory;

    @Builder
    public GetProductDetailResponseDto(String productName, Long aiEstimatedPrice, String description,
                                       List<GetProductImageResponseDto> images, GetCategoryResponseDto mainCategory,
                                       GetCategoryResponseDto subCategory) {
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.description = description;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public static GetProductDetailResponseDto from(ProductReadMongoEntity productRead) {
        return GetProductDetailResponseDto.builder()
                .productName(productRead.getProductName())
                .aiEstimatedPrice(productRead.getAiEstimatedPrice())
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
                .description(description)
                .images(images.stream().map(GetProductImageResponseDto::toVo).toList())
                .mainCategory(mainCategory.toVo())
                .subCategory(subCategory.toVo())
                .build();
    }
}
