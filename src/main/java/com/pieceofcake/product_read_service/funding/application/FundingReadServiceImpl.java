package com.pieceofcake.product_read_service.funding.application;

import com.pieceofcake.product_read_service.funding.dto.in.CreateFundingEventDto;
import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FundingReadServiceImpl implements FundingReadService {

    private final ProductReadMongoRepository productReadMongoRepository;

    @Override
    public void getFundingRead(String uuid) {
        ProductReadMongoEntity product = productReadMongoRepository.findByProductUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("No product found with UUID:"+uuid ));
    }

    @Override
    public void createFundingRead(CreateFundingEventDto createFundingEventDto) {
        ProductReadMongoEntity product = productReadMongoRepository.findByProductUuid(createFundingEventDto.getProductUuid())
                .orElseThrow(() -> new IllegalArgumentException("No product found with UUID: " + createFundingEventDto.getProductUuid()));
        product.createFundingRead(createFundingEventDto.toEntity());
        productReadMongoRepository.save(product);
    }

    @Override
    public void deleteFundingRead(String productUuid) {
        ProductReadMongoEntity product = productReadMongoRepository.findByProductUuid(productUuid)
                .orElseThrow(() -> new IllegalArgumentException("No product found with UUID: " + productUuid));

        product.createFundingRead(null);
        productReadMongoRepository.save(product);
    }
}
