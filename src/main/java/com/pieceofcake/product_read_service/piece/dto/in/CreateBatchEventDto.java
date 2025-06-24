package com.pieceofcake.product_read_service.piece.dto.in;

import com.pieceofcake.product_read_service.kafka.event.BatchReadEvent;
import com.pieceofcake.product_read_service.piece.entity.PieceRead;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateBatchEventDto {
    private String pieceProductUuid;
    private Long tradeQuantity;
    private Long closingPrice;

    @Builder
    public CreateBatchEventDto(String pieceProductUuid, Long tradeQuantity, Long closingPrice) {
        this.pieceProductUuid = pieceProductUuid;
        this.tradeQuantity = tradeQuantity;
        this.closingPrice = closingPrice;
    }

    public static CreateBatchEventDto from(BatchReadEvent event) {
        return CreateBatchEventDto.builder()
                .pieceProductUuid(event.getPieceProductUuid())
                .tradeQuantity(event.getTradeQuantity())
                .closingPrice(event.getClosingPrice())
                .build();
    }

    public PieceRead toEntity(PieceRead pieceRead) {
        return PieceRead.builder()
                .pieceProductUuid(pieceRead.getPieceProductUuid())
                .isTrading(pieceRead.getIsTrading())
                .tradeQuantity(tradeQuantity)
                .closingPrice(closingPrice)
                .build();
    }
}
