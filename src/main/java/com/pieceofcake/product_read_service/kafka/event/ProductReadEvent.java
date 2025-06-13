package com.pieceofcake.product_read_service.kafka.event;

import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ProductReadEvent {
    private String eventType;
    private String productUuid;
    private String productName;
    private Long aiEstimatedPrice;
    private Long purchasePrice;
    private String productStatus;
    private String storageLocation;
    private String description;
    private List<ProductImageReadEvent> images;
    private Integer mainCategoryId;
    private Integer subCategoryId;
}
