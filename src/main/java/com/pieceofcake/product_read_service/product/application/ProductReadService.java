package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductDetailResponseDto;

public interface ProductReadService {

    void createProductRead(CreateProductEventDto createProductEventDto);

    void updateProductRead(UpdateProductEventDto updateProductEventDto);

    void deleteProductRead(String productUuid);

    GetProductDetailResponseDto getProductDetail(String productUuid);
}
