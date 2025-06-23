package com.pieceofcake.product_read_service.piece.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetPieceResponseVo {
    private String pieceProductUuid;
    private Boolean isTrading;
    private Long tradeQuantity;
    private Long closingPrice;

    @Builder
    public GetPieceResponseVo(String pieceProductUuid, Boolean isTrading, Long tradeQuantity, Long closingPrice) {
        this.pieceProductUuid = pieceProductUuid;
        this.isTrading = isTrading;
        this.tradeQuantity = tradeQuantity;
        this.closingPrice = closingPrice;
    }
}
