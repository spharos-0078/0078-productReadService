package com.pieceofcake.product_read_service.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProductDetailResponseVo {
    private String productName;
    private Long aiEstimatedPrice;
    private String description;
    private List<GetProductImageResponseVo> images;
    private GetCategoryResponseVo mainCategory;
    private GetCategoryResponseVo subCategory;

    @Builder
    public GetProductDetailResponseVo(String productName, Long aiEstimatedPrice, String description,
                                      List<GetProductImageResponseVo> images, GetCategoryResponseVo mainCategory,
                                      GetCategoryResponseVo subCategory) {
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.description = description;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }
}
