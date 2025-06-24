package com.pieceofcake.product_read_service.piece.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PieceRead {
    private String pieceProductUuid;
    private Boolean isTrading;
    private Long tradeQuantity;
    private Long closingPrice;

    @Builder
    public PieceRead(String pieceProductUuid, Boolean isTrading, Long tradeQuantity, Long closingPrice) {
        this.pieceProductUuid = pieceProductUuid;
        this.isTrading = isTrading;
        this.tradeQuantity = tradeQuantity;
        this.closingPrice = closingPrice;
    }
}
