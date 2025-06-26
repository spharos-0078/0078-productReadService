package com.pieceofcake.product_read_service.kafka.event;

import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
public class ProductStatusEvent {
    private String productUuid;
    private ProductStatus productStatus;
}
