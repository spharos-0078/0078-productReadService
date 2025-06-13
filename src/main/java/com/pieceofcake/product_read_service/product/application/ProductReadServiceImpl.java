package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductReadMongoRepository productReadMongoRepository;

    @Override
    public void createProductRead(CreateProductEventDto createProductEventDto) {
        productReadMongoRepository.save(createProductEventDto.toEntity());
    }

    @Override
    public void updateProductRead(CreateProductEventDto createProductEventDto) {
        ProductReadMongoEntity productRead = productReadMongoRepository.findByProductUuid(createProductEventDto.getProductUuid())
                .orElseThrow(() -> new NoSuchElementException("No product found with UUID: " + createProductEventDto.getProductUuid()));

        productReadMongoRepository.save(createProductEventDto.toEntity(productRead));
    }

    @Override
    public void deleteProductRead(String productUuid) {
        productReadMongoRepository.deleteByProductUuid(productUuid);
    }
}
