package com.pieceofcake.product_read_service.funding.application;

import com.pieceofcake.product_read_service.common.entity.BaseResponseStatus;
import com.pieceofcake.product_read_service.common.exception.BaseException;
import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;
import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.funding.dto.out.GetFundingDetailResponseDto;
import com.pieceofcake.product_read_service.funding.dto.out.GetFundingUuidListResponseDto;
import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FundingReadServiceImpl implements FundingReadService {

    private final ProductReadMongoRepository productReadMongoRepository;

    @Override
    public GetFundingUuidListResponseDto getFundingFilterUuid(FundingFilterRequestDto fundingFilterRequestDto) {
        return GetFundingUuidListResponseDto.from(productReadMongoRepository.searchWithFilters(fundingFilterRequestDto)
                .map(entity->entity.getFundingRead().getFundingUuid()));
    }

    @Override
    public GetFundingDetailResponseDto getFundingDetail(String fundingUuid) {
        return GetFundingDetailResponseDto.from(productReadMongoRepository.findByFundingRead_FundingUuid(fundingUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_FUNDING)));
    }


//    @Override
//    public void getFundingRead(String uuid) {
//        ProductReadMongoEntity product = productReadMongoRepository.findByProductUuid(uuid)
//                .orElseThrow(() -> new IllegalArgumentException("No product found with UUID:"+uuid ));
//        System.out.println(product.toString());
//    }

    @Override
    public void createFundingRead(CreateFundingEventDto createFundingEventDto) {
        ProductReadMongoEntity product = productReadMongoRepository.findByProductUuid(createFundingEventDto.getProductUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_FUNDING));
        product.createFundingRead(createFundingEventDto.toEntity());
        productReadMongoRepository.save(product);
    }

    @Override
    public void deleteFundingRead(String productUuid) {
        ProductReadMongoEntity product = productReadMongoRepository.findByProductUuid(productUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_FUNDING));

        product.createFundingRead(null);
        productReadMongoRepository.save(product);
    }
}
