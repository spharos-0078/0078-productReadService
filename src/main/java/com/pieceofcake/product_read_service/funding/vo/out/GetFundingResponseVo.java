package com.pieceofcake.product_read_service.funding.vo.out;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class GetFundingResponseVo {
    private String fundingUuid;
    private Long fundingAmount;
    private Long piecePrice;
    private Integer totalPieces;
    private Integer remainingPieces;
    private Integer availablePieces;
    private LocalDateTime fundingDeadline;
    private String fundingStatus;

    @Builder
    public GetFundingResponseVo(
            String fundingUuid,
            Long fundingAmount,
            Long piecePrice,
            Integer totalPieces,
            Integer remainingPieces,
            Integer availablePieces,
            LocalDateTime fundingDeadline,
            String fundingStatus
    ){
        this.fundingUuid = fundingUuid;
        this.fundingAmount = fundingAmount;
        this.piecePrice = piecePrice;
        this.totalPieces = totalPieces;
        this.remainingPieces = remainingPieces;
        this.availablePieces = availablePieces;
        this.fundingDeadline = fundingDeadline;
        this.fundingStatus = fundingStatus;
    }
}