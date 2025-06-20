package com.pieceofcake.product_read_service.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCategoryResponseVo {
    private Integer categoryId;
    private String categoryName;

    @Builder
    public GetCategoryResponseVo(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
