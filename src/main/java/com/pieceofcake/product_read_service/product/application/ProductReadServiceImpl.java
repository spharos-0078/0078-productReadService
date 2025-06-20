package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.common.entity.BaseResponseStatus;
import com.pieceofcake.product_read_service.common.exception.BaseException;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductDetailResponseDto;
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        productReadMongoRepository.save(updateProductEventDto.toEntity(productRead));
    }

    @Override
    public void deleteProductRead(String productUuid) {
        productReadMongoRepository.deleteByProductUuid(productUuid);
    }

    @Override
    public GetProductDetailResponseDto getProductDetail(String productUuid) {
        ProductReadMongoEntity productRead = productReadMongoRepository.findByProductUuid(productUuid)
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        return GetProductDetailResponseDto.from(productRead);
    }
}
