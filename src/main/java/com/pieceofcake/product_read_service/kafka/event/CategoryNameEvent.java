package com.pieceofcake.product_read_service.kafka.event;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
public class CategoryNameEvent {
    private String productUuid;
    private String mainCategoryName;
    private String subCategoryName;
}

