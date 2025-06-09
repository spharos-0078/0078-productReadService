package com.pieceofcake.product_read_service.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "product_image_read")
public class ProductReadImageEntity {
    @Id
    private String id;
    private String imageUrl;
    private Integer imageIndex;
    private Boolean isThumbnail;

    @Builder
    public ProductReadImageEntity(String id, String imageUrl, Integer imageIndex, Boolean isThumbnail) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
        this.isThumbnail = isThumbnail;
    }
}
