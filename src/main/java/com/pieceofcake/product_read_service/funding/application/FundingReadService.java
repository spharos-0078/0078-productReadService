package com.pieceofcake.product_read_service.funding.application;

import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;
import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.funding.dto.in.UpdateRemainPiecesEventDto;
import com.pieceofcake.product_read_service.funding.dto.out.GetFundingDetailResponseDto;
import com.pieceofcake.product_read_service.funding.dto.out.GetFundingUuidListResponseDto;

public interface FundingReadService {
    GetFundingUuidListResponseDto getFundingFilterUuid(FundingFilterRequestDto fundingFilterRequestDto);
    GetFundingDetailResponseDto getFundingDetail(String fundingUuid);
    void createFundingRead(CreateFundingEventDto createFundingEventDto);
    void updateRemainPieces(UpdateRemainPiecesEventDto updateRemainPiecesEventDto);
    void deleteFundingRead(String productUuid);
}