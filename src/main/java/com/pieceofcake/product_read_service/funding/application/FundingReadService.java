package com.pieceofcake.product_read_service.funding.application;

import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;
import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.funding.dto.out.GetFundingDetailResponseDto;
import org.springframework.data.domain.Page;

public interface FundingReadService {
    Page<String> getFundingFilterUuid(FundingFilterRequestDto fundingFilterRequestDto);
    GetFundingDetailResponseDto getFundingDetail(String fundingUuid);
    void createFundingRead(CreateFundingEventDto createFundingEventDto);
//    void updateFundingRead(CreateFundingEventDto createFundingEventDto);
    void deleteFundingRead(String productUuid);
}