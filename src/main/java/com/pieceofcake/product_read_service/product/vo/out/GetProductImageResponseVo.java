package com.pieceofcake.product_read_service.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProductImageResponseVo {
    private Integer imageIndex;
    private String imageUrl;
    private Boolean isThumbnail;

    @Builder
    public GetProductImageResponseVo(Integer imageIndex, String imageUrl, Boolean isThumbnail) {
        this.imageIndex = imageIndex;
        this.imageUrl = imageUrl;
        this.isThumbnail = isThumbnail;
    }
}
