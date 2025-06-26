package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductReadCustomMongoRepositoryImpl implements ProductReadCustomMongoRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ProductRead> searchProductWithFilters(GetProductFilterRequestDto dto) {
        Query query = new Query();

        // 메인 카테고리 필터
        if (dto.getMain() != null) {
            query.addCriteria(Criteria.where("mainCategory.categoryId").is(dto.getMain()));
        }

        // 서브 카테고리 필터
        if (dto.getSub() != null) {
            query.addCriteria(Criteria.where("subCategory.categoryId").is(dto.getSub()));
        }

        // 상품 이름 검색 (대소문자 구분 없음)
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            query.addCriteria(Criteria.where("productName").regex(dto.getName(), "i"));
        }

        // name()으로 문자열 비교
        if (dto.getStatus() != null) {
            query.addCriteria(Criteria.where("productStatus").is(dto.getStatus().name()));
        }

        // 정렬: createdAt 기준으로 최신순
        query.with(dto.getPageable().getSort());

        // 페이징 처리
        long total = mongoTemplate.count(query, ProductRead.class);

        query.with(dto.getPageable());
        List<ProductRead> list = mongoTemplate.find(query, ProductRead.class);

        return new PageImpl<>(list, dto.getPageable(), total);
    }
}
