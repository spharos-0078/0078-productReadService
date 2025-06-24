package com.pieceofcake.product_read_service.kafka.event;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class PieceReadEvent {
    String productUuid;
    String pieceProductUuid;
    Boolean isTrading;
}
