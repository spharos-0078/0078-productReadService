package com.pieceofcake.product_read_service.funding.dto.in;

import com.pieceofcake.product_read_service.kafka.event.FundingReadEvent;
import com.pieceofcake.product_read_service.kafka.event.PieceReadEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRemainPiecesEventDto {
    private String fundingUuid;
    private Integer remainPieces;

    public static UpdateRemainPiecesEventDto from(FundingReadEvent event){
        return UpdateRemainPiecesEventDto.builder()
                .fundingUuid(event.getFundingUuid())
                .remainPieces(event.getRemainingPieces())
                .build();
    }
}
