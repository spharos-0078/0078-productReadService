package com.pieceofcake.product_read_service.kafka.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class BatchReadEvent {
    private String pieceProductUuid;
    private Long tradeQuantity;
    private Long closingPrice;
}
