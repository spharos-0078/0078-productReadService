package com.pieceofcake.product_read_service.piece.dto.out;

import com.pieceofcake.product_read_service.piece.vo.out.GetPieceDetailResponseVo;
import com.pieceofcake.product_read_service.product.dto.out.GetCategoryResponseDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductImageResponseDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPieceDetailResponseDto {
    private String productUuid;
    private GetCategoryResponseDto mainCategory;
    private GetCategoryResponseDto subCategory;
    private String productName;
    private String description;
    private Long aiEstimatedPrice;
    private String aiEstimatedDescription;
    private List<GetProductImageResponseDto> images;
    private GetPieceResponseDto piece;

    @Builder
    public GetPieceDetailResponseDto(String productUuid, GetCategoryResponseDto mainCategory, GetCategoryResponseDto subCategory,
                                     String productName, String description, Long aiEstimatedPrice, String aiEstimatedDescription, List<GetProductImageResponseDto> images, GetPieceResponseDto piece) {
        this.productUuid = productUuid;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.productName = productName;
        this.description = description;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.images = images;
        this.piece = piece;
    }

    public static GetPieceDetailResponseDto from(ProductRead productRead) {
        return GetPieceDetailResponseDto.builder()
                .productUuid(productRead.getProductUuid())
                .mainCategory(GetCategoryResponseDto.from(productRead.getMainCategory()))
                .subCategory(GetCategoryResponseDto.from(productRead.getSubCategory()))
                .productName(productRead.getProductName())
                .description(productRead.getDescription())
                .aiEstimatedPrice(productRead.getAiEstimatedPrice())
                .aiEstimatedDescription(productRead.getAiEstimatedDescription())
                .images(productRead.getImages().stream().map(GetProductImageResponseDto::from).toList())
                .piece(GetPieceResponseDto.from(productRead.getPieceRead()))
                .build();
    }

    public GetPieceDetailResponseVo toVo() {
        return GetPieceDetailResponseVo.builder()
                .productUuid(productUuid)
                .mainCategory(mainCategory.toVo())
                .subCategory(subCategory.toVo())
                .productName(productName)
                .description(description)
                .aiEstimatedPrice(aiEstimatedPrice)
                .aiEstimatedDescription(aiEstimatedDescription)
                .images(images.stream().map(GetProductImageResponseDto::toVo).toList())
                .piece(piece.toVo())
                .build();
    }
}
