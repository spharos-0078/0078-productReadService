package com.pieceofcake.product_read_service.piece.dto.in;

import com.pieceofcake.product_read_service.kafka.event.PieceReadEvent;
import com.pieceofcake.product_read_service.piece.entity.PieceRead;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePieceEventDto {
    private String productUuid;
    private String pieceProductUuid;
    private Boolean isTrading;
    private String status;

    @Builder
    public CreatePieceEventDto(String productUuid, String pieceProductUuid, Boolean isTrading, String status) {
        this.productUuid = productUuid;
        this.pieceProductUuid = pieceProductUuid;
        this.isTrading = isTrading;
        this.status = status;
    }

    public static CreatePieceEventDto from(PieceReadEvent event) {
        return CreatePieceEventDto.builder()
                .productUuid(event.getProductUuid())
                .pieceProductUuid(event.getPieceProductUuid())
                .isTrading(event.getIsTrading())
                .status(event.getStatus())
                .build();
    }

    public PieceRead toEntity() {
        return PieceRead.builder()
                .pieceProductUuid(pieceProductUuid)
                .isTrading(isTrading)
                .pieceProductStatus(status)
                .build();
    }
}
