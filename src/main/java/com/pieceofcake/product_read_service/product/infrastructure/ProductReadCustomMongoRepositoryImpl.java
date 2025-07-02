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

        // 상품 이름 검색 - 텍스트 인덱스 활용
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            String searchTerm = dto.getName().trim();
            // 정규식 대신 텍스트 검색 또는 prefix 검색 사용
            if (searchTerm.length() >= 3) {
                query.addCriteria(Criteria.where("productName").regex("^" + searchTerm, "i"));
            } else {
                query.addCriteria(Criteria.where("productName").regex(searchTerm, "i"));
            }
        }

        // 상태 필터
        if (dto.getStatus() != null) {
            query.addCriteria(Criteria.where("productStatus").is(dto.getStatus().name()));
        }

        // 정렬: createdAt 기준으로 최신순
        query.with(dto.getPageable().getSort());

        // 성능 최적화: 페이징된 결과만 조회
        query.with(dto.getPageable());
        List<ProductRead> list = mongoTemplate.find(query, ProductRead.class);

        // 카운트 쿼리 최적화: 작은 페이지의 경우에만 카운트
        long total;
        if (dto.getPageable().getPageNumber() == 0 && list.size() < dto.getPageable().getPageSize()) {
            total = list.size();
        } else {
            // 카운트 쿼리에서 정렬 제거하여 성능 향상
            Query countQuery = new Query();
            if (dto.getMain() != null) {
                countQuery.addCriteria(Criteria.where("mainCategory.categoryId").is(dto.getMain()));
            }
            if (dto.getSub() != null) {
                countQuery.addCriteria(Criteria.where("subCategory.categoryId").is(dto.getSub()));
            }
            if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
                String searchTerm = dto.getName().trim();
                if (searchTerm.length() >= 3) {
                    countQuery.addCriteria(Criteria.where("productName").regex("^" + searchTerm, "i"));
                } else {
                    countQuery.addCriteria(Criteria.where("productName").regex(searchTerm, "i"));
                }
            }
            if (dto.getStatus() != null) {
                countQuery.addCriteria(Criteria.where("productStatus").is(dto.getStatus().name()));
            }
            total = mongoTemplate.count(countQuery, ProductRead.class);
        }

        return new PageImpl<>(list, dto.getPageable(), total);
    }
}
