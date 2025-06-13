package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;

public interface ProductReadService {

    void createProductRead(CreateProductEventDto createProductEventDto);
    void updateProductRead(CreateProductEventDto createProductEventDto);
    void deleteProductRead(String productUuid);
}
