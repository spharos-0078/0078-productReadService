package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.kafka.event.ProductStatusEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateProductStatusEventDto {
    private String productUuid;
    private String productStatus;

    @Builder
    public UpdateProductStatusEventDto(String productUuid, String productStatus) {
        this.productUuid = productUuid;
        this.productStatus = productStatus;
    }

    public static UpdateProductStatusEventDto from(ProductStatusEvent event) {
        return UpdateProductStatusEventDto.builder()
                .productUuid(event.getProductUuid())
                .productStatus(event.getProductStatus().name())
                .build();
    }
}
