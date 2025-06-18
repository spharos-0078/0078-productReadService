package com.pieceofcake.product_read_service.funding.dto.in;

import com.pieceofcake.product_read_service.funding.entity.FundingRead;
import com.pieceofcake.product_read_service.kafka.event.FundingReadEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateFundingEventDto {
    private String fundingUuid;
    private String productUuid;
    private Long fundingAmount;
    private Long piecePrice;
    private Integer totalPieces;
    private Integer remainingPieces;
    private LocalDateTime fundingDeadline;
    private String fundingStatus;

    @Builder
    public CreateFundingEventDto(
            String fundingUuid, String productUuid, Long fundingAmount, Long piecePrice, Integer totalPieces,
            Integer remainingPieces, LocalDateTime fundingDeadline, String fundingStatus) {
        this.fundingUuid = fundingUuid;
        this.productUuid = productUuid;
        this.fundingAmount = fundingAmount;
        this.piecePrice = piecePrice;
        this.totalPieces = totalPieces;
        this.remainingPieces = remainingPieces;
        this.fundingDeadline = fundingDeadline;
        this.fundingStatus = fundingStatus;
    }

    public static CreateFundingEventDto from(FundingReadEvent fundingEvent){
        return CreateFundingEventDto.builder()
                .fundingUuid(fundingEvent.getFundingUuid())
                .productUuid(fundingEvent.getProductUuid())
                .fundingAmount(fundingEvent.getFundingAmount())
                .piecePrice(fundingEvent.getPiecePrice())
                .totalPieces(fundingEvent.getTotalPieces())
                .remainingPieces(fundingEvent.getRemainingPieces())
                .fundingDeadline(fundingEvent.getFundingDeadline())
                .fundingStatus(fundingEvent.getFundingStatus())
                .build();
    }

    public FundingRead toEntity(){
        return FundingRead.builder()
                .fundingUuid(fundingUuid)
                .fundingAmount(fundingAmount)
                .piecePrice(piecePrice)
                .totalPieces(totalPieces)
                .remainingPieces(remainingPieces)
                .fundingDeadline(fundingDeadline)
                .fundingStatus(fundingStatus)
                .build();
    }
}
