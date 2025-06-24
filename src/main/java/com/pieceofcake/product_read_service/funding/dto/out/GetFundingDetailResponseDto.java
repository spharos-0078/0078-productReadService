package com.pieceofcake.product_read_service.funding.dto.out;

import com.pieceofcake.product_read_service.funding.vo.out.GetFundingDetailResponseVo;
import com.pieceofcake.product_read_service.product.dto.out.GetCategoryResponseDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductImageResponseDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetFundingDetailResponseDto {
    private String productUuid;
    private GetCategoryResponseDto mainCategory;
    private GetCategoryResponseDto subCategory;
    private String productName;
    private String description;
    private Long aiEstimatedPrice;
    private String aiEstimatedDescription;
    private List<GetProductImageResponseDto> images;
    private GetFundingResponseDto funding;

    @Builder
    public GetFundingDetailResponseDto(
            String productUuid,
            GetCategoryResponseDto mainCategory,
            GetCategoryResponseDto subCategory,
            String productName,
            String description,
            Long aiEstimatedPrice,
            String aiEstimatedDescription,
            List<GetProductImageResponseDto> images,
            GetFundingResponseDto funding
    ){
        this.productUuid = productUuid;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.productName = productName;
        this.description = description;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.images = images;
        this.funding = funding;
    }

    public static GetFundingDetailResponseDto from(ProductRead entity){
        return GetFundingDetailResponseDto.builder()
                .productUuid(entity.getProductUuid())
                .mainCategory(GetCategoryResponseDto.from(entity.getMainCategory()))
                .subCategory(GetCategoryResponseDto.from(entity.getSubCategory()))
                .productName(entity.getProductName())
                .description(entity.getDescription())
                .aiEstimatedPrice(entity.getAiEstimatedPrice())
                .aiEstimatedDescription(entity.getAiEstimatedDescription())
                .images(entity.getImages().stream().map(GetProductImageResponseDto::from).toList())
                .funding(GetFundingResponseDto.from(entity.getFundingRead()))
                .build();
    }

    public GetFundingDetailResponseVo toVo(){
        return GetFundingDetailResponseVo.builder()
                .productUuid(productUuid)
                .mainCategory(mainCategory.toVo())
                .subCategory(subCategory.toVo())
                .productName(productName)
                .description(description)
                .aiEstimatedPrice(aiEstimatedPrice)
                .aiEstimatedDescription(aiEstimatedDescription)
                .images(images.stream().map(GetProductImageResponseDto::toVo).toList())
                .funding(funding.toVo())
                .build();
    }
}