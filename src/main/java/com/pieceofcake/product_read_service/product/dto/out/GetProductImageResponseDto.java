package com.pieceofcake.product_read_service.product.dto.out;

import com.pieceofcake.product_read_service.product.entity.ProductImageRead;
import com.pieceofcake.product_read_service.product.vo.out.GetProductImageResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProductImageResponseDto {
    private Integer imageIndex;
    private String imageUrl;
    private Boolean isThumbnail;

    @Builder
    public GetProductImageResponseDto(Integer imageIndex, String imageUrl, Boolean isThumbnail) {
        this.imageIndex = imageIndex;
        this.imageUrl = imageUrl;
        this.isThumbnail = isThumbnail;
    }

    public static GetProductImageResponseDto from(ProductImageRead productReadImage){
        return GetProductImageResponseDto.builder()
                .imageIndex(productReadImage.getImageIndex())
                .imageUrl(productReadImage.getImageUrl())
                .isThumbnail(productReadImage.getIsThumbnail())
                .build();
    }

    public GetProductImageResponseVo toVo() {
        return GetProductImageResponseVo.builder()
                .imageIndex(imageIndex)
                .imageUrl(imageUrl)
                .isThumbnail(isThumbnail)
                .build();
    }
}
