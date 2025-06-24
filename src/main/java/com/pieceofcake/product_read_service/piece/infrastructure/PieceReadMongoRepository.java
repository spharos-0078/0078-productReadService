package com.pieceofcake.product_read_service.piece.infrastructure;

import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.piece.dto.in.GetPieceFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import org.springframework.data.domain.Page;

public interface PieceReadMongoRepository {
    Page<ProductRead> searchPieceProductWithFilters(GetPieceFilterRequestDto getPieceFilterRequestDto);
}
