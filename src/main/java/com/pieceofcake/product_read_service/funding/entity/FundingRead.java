package com.pieceofcake.product_read_service.funding.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FundingRead {
    private String fundingUuid;
    private Long fundingAmount;
    private Long piecePrice;
    private Integer totalPieces;
    private Integer remainingPieces;
    private LocalDateTime fundingDeadline;
    private String fundingStatus;

    @Builder
    public FundingRead(
            String fundingUuid, Long fundingAmount, Long piecePrice, Integer totalPieces,
            Integer remainingPieces, LocalDateTime fundingDeadline, String fundingStatus) {
        this.fundingUuid = fundingUuid;
        this.fundingAmount = fundingAmount;
        this.piecePrice = piecePrice;
        this.totalPieces = totalPieces;
        this.remainingPieces = remainingPieces;
        this.fundingDeadline = fundingDeadline;
        this.fundingStatus = fundingStatus;
    }
}
