package com.pieceofcake.product_read_service.funding.infrastructure;

import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import org.springframework.data.domain.Page;

public interface FundingReadMongoRepository{
    Page<ProductReadMongoEntity> searchWithFilters(FundingFilterRequestDto dto);
}
