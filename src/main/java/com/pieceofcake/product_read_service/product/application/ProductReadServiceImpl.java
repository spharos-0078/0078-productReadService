package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.common.entity.BaseResponseStatus;
import com.pieceofcake.product_read_service.common.exception.BaseException;
import com.pieceofcake.product_read_service.funding.dto.out.GetFundingDetailResponseDto;
import com.pieceofcake.product_read_service.piece.dto.out.GetPieceUuidListResponseDto;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductDetailResponseDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductUuidListResponseDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import com.pieceofcake.product_read_service.product.vo.out.GetProductDetailResponseVo;
import com.pieceofcake.product_read_service.product.vo.out.GetProductUuidListResponseVo;
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

    @Override
    public void updateProductRead(UpdateProductEventDto updateProductEventDto) {
        ProductRead productRead = productReadMongoRepository.findByProductUuid(updateProductEventDto.getProductUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        productReadMongoRepository.save(updateProductEventDto.toEntity(productRead));
    }

    @Override
    public void deleteProductRead(String productUuid) {
        productReadMongoRepository.deleteByProductUuid(productUuid);
    }

    @Override
    public GetProductDetailResponseDto getProductDetail(String productUuid) {
        ProductRead productRead = productReadMongoRepository.findByProductUuid(productUuid)
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        return GetProductDetailResponseDto.from(productRead);
    }

    @Override
    public GetProductUuidListResponseDto getProductFilterUuid(GetProductFilterRequestDto getProductFilterRequestDto) {
        return GetProductUuidListResponseDto.from(productReadMongoRepository.searchProductWithFilters(getProductFilterRequestDto));
    }

    @Override
    public void updateProductStatus(String productUuid, ProductStatus productStatus) {
        ProductRead product = productReadMongoRepository.findByProductUuid(productUuid)
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.updateProductStatus(productStatus);
    }
}
