package com.pieceofcake.product_read_service.funding.infrastructure;

import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import org.springframework.data.domain.Page;

public interface FundingReadMongoRepository{
    Page<ProductRead> searchWithFilters(FundingFilterRequestDto dto);
    void updateRemainPieces(String fundingUuid, int remainPieces);
}
