package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.kafka.event.ProductImageReadEvent;
import com.pieceofcake.product_read_service.product.entity.ProductReadImageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CreateProductImageEventDto {
    private String imageUrl;
    private Integer imageIndex;
    private Boolean isThumbnail;

    @Builder
    public CreateProductImageEventDto(String imageUrl, Integer imageIndex, Boolean isThumbnail) {
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
        this.isThumbnail = isThumbnail;
    }

    public static CreateProductImageEventDto from(ProductImageReadEvent productImageReadEvent) {
        return CreateProductImageEventDto.builder()
                .imageUrl(productImageReadEvent.getImageUrl())
                .imageIndex(productImageReadEvent.getImageIndex())
                .isThumbnail(productImageReadEvent.getIsThumbnail())
                .build();
    }

    public ProductReadImageEntity toEntity() {
        return ProductReadImageEntity.builder()
                .imageUrl(imageUrl)
                .imageIndex(imageIndex)
                .isThumbnail(isThumbnail)
                .build();
    }
}
