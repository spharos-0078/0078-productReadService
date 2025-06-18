package com.pieceofcake.product_read_service.funding.application;

import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;

public interface FundingReadService {
    void getFundingRead(String uuid);
    void createFundingRead(CreateFundingEventDto createFundingEventDto);
//    void updateFundingRead(CreateFundingEventDto createFundingEventDto);
    void deleteFundingRead(String productUuid);
}