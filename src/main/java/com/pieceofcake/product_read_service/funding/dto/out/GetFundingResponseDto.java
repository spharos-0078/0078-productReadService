package com.pieceofcake.product_read_service.funding.dto.out;

import com.pieceofcake.product_read_service.funding.entity.FundingRead;
import com.pieceofcake.product_read_service.funding.vo.out.GetFundingResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetFundingResponseDto {
    private String fundingUuid;
    private Long fundingAmount;
    private Long piecePrice;
    private Integer totalPieces;
    private Integer remainingPieces;
    private Integer availablePieces;
    private LocalDateTime fundingDeadline;
    private String fundingStatus;

    @Builder
    public GetFundingResponseDto(
            String fundingUuid,
            Long fundingAmount,
            Long piecePrice,
            Integer totalPieces,
            Integer remainingPieces,
            LocalDateTime fundingDeadline,
            String fundingStatus
    ) {
        this.fundingUuid = fundingUuid;
        this.fundingAmount = fundingAmount;
        this.piecePrice = piecePrice;
        this.totalPieces = totalPieces;
        this.remainingPieces = remainingPieces;
        this.availablePieces = totalPieces - remainingPieces;
        this.fundingDeadline = fundingDeadline;
        this.fundingStatus = fundingStatus;
    }

    public static GetFundingResponseDto from(FundingRead fundingRead) {
        return GetFundingResponseDto.builder()
                .fundingUuid(fundingRead.getFundingUuid())
                .fundingAmount(fundingRead.getFundingAmount())
                .piecePrice(fundingRead.getPiecePrice())
                .totalPieces(fundingRead.getTotalPieces())
                .remainingPieces(fundingRead.getRemainingPieces())
                .fundingDeadline(fundingRead.getFundingDeadline())
                .fundingStatus(fundingRead.getFundingStatus())
                .build();
    }

    public GetFundingResponseVo toVo(){
        return GetFundingResponseVo.builder()
                .fundingUuid(fundingUuid)
                .fundingAmount(fundingAmount)
                .piecePrice(piecePrice)
                .totalPieces(totalPieces)
                .remainingPieces(remainingPieces)
                .availablePieces(availablePieces)
                .fundingDeadline(fundingDeadline)
                .fundingStatus(fundingStatus)
                .build();
    }
}