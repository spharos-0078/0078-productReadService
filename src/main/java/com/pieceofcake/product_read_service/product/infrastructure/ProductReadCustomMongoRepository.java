package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.piece.dto.in.GetPieceFilterRequestDto;
import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import org.springframework.data.domain.Page;

public interface ProductReadCustomMongoRepository {
    Page<ProductRead> searchProductWithFilters(GetProductFilterRequestDto getProductFilterRequestDto);
}
