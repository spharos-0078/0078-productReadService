package com.pieceofcake.product_read_service.kafka.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ProductImageReadEvent {
    private String imageUrl;
    private Integer imageIndex;
    private Boolean isThumbnail;
}
