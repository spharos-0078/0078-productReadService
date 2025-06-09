package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductReadMongoRepository productReadMongoRepository;

    @Override
    public void createProductRead(CreateProductEventDto createProductEventDto) {
        productReadMongoRepository.save(createProductEventDto.toEntity());
    }
}
