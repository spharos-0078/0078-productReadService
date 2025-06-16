package com.pieceofcake.product_read_service.kafka.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CategoryReadEvent {
    private Integer categoryId;
    private String categoryName;
}
