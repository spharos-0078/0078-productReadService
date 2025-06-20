package com.pieceofcake.product_read_service.product.dto.out;

import com.pieceofcake.product_read_service.product.entity.CategoryReadEntity;
import com.pieceofcake.product_read_service.product.vo.out.GetCategoryResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCategoryResponseDto {
    private Integer categoryId;
    private String categoryName;

    @Builder
    public GetCategoryResponseDto(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static GetCategoryResponseDto from(CategoryReadEntity mainCategory) {
        return GetCategoryResponseDto.builder()
                .categoryId(mainCategory.getCategoryId())
                .categoryName(mainCategory.getCategoryName())
                .build();
    }

    public GetCategoryResponseVo toVo() {
        return GetCategoryResponseVo.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
