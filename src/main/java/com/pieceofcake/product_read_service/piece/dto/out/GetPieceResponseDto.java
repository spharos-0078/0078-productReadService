package com.pieceofcake.product_read_service.piece.dto.out;

import com.pieceofcake.product_read_service.piece.entity.PieceRead;
import com.pieceofcake.product_read_service.piece.vo.out.GetPieceResponseVo;
import lombok.Builder;

@Builder
public class GetPieceResponseDto {
    private String pieceProductUuid;
    private Boolean isTrading;
    private Long tradeQuantity;
    private Long closingPrice;
    private String status;

    @Builder
    public GetPieceResponseDto(String pieceProductUuid, Boolean isTrading, Long tradeQuantity, Long closingPrice, String status) {
        this.pieceProductUuid = pieceProductUuid;
        this.isTrading = isTrading;
        this.tradeQuantity = tradeQuantity;
        this.closingPrice = closingPrice;
        this.status = status;
    }

    public static GetPieceResponseDto from(PieceRead pieceRead) {
        return GetPieceResponseDto.builder()
                .pieceProductUuid(pieceRead.getPieceProductUuid())
                .tradeQuantity(pieceRead.getTradeQuantity())
                .closingPrice(pieceRead.getClosingPrice())
                .isTrading(pieceRead.getIsTrading())
                .status(pieceRead.getPieceProductStatus())
                .build();
    }

    public GetPieceResponseVo toVo() {
        return GetPieceResponseVo.builder()
                .pieceProductUuid(pieceProductUuid)
                .isTrading(isTrading)
                .tradeQuantity(tradeQuantity)
                .closingPrice(closingPrice)
                .status(status)
                .build();
    }
}
