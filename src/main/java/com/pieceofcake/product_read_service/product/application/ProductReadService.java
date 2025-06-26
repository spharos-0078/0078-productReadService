package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.funding.dto.out.GetFundingDetailResponseDto;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductDetailResponseDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductUuidListResponseDto;
import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import com.pieceofcake.product_read_service.product.vo.out.GetProductUuidListResponseVo;

public interface ProductReadService {

    void createProductRead(CreateProductEventDto createProductEventDto);

    void updateProductRead(UpdateProductEventDto updateProductEventDto);

    void deleteProductRead(String productUuid);

    GetProductDetailResponseDto getProductDetail(String productUuid);

    GetProductUuidListResponseDto getProductFilterUuid(GetProductFilterRequestDto from);

    void updateProductStatus(String productUuid, ProductStatus productStatus);
}
