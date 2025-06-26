package com.pieceofcake.product_read_service.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProductDetailResponseVo {
    private String productName;
    private Long aiEstimatedPrice;
    private String aiEstimatedDescription;
    private String description;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private List<GetProductImageResponseVo> images;
    private GetCategoryResponseVo mainCategory;
    private GetCategoryResponseVo subCategory;

    @Builder
    public GetProductDetailResponseVo(String productName, Long aiEstimatedPrice, String aiEstimatedDescription,
                                      String description, Long purchasePrice, String productStatus, String storageLocation,
                                      List<GetProductImageResponseVo> images, GetCategoryResponseVo mainCategory, GetCategoryResponseVo subCategory) {
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
}
