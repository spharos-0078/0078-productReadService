package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
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
    public void updateProductRead(UpdateProductEventDto updateProductEventDto) {
        ProductReadMongoEntity productRead = productReadMongoRepository.findByProductUuid(updateProductEventDto.getProductUuid())
                .orElseThrow(() -> new NoSuchElementException("No product found with UUID: " + updateProductEventDto.getProductUuid()));

        productReadMongoRepository.save(updateProductEventDto.toEntity(productRead));
    }

    @Override
    public void deleteProductRead(String productUuid) {
        productReadMongoRepository.deleteByProductUuid(productUuid);
    }
}
