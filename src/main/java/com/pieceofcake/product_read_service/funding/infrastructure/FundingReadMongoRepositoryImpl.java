package com.pieceofcake.product_read_service.funding.infrastructure;

import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FundingReadMongoRepositoryImpl implements FundingReadMongoRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ProductRead> searchWithFilters(FundingFilterRequestDto dto) {
        Query query = new Query();

        query.addCriteria(Criteria.where("fundingRead").ne(null));
        query.addCriteria(Criteria.where("fundingRead.fundingStatus").is("FUNDING"));

        if (dto.getMain() != null) {
            query.addCriteria(Criteria.where("mainCategory.categoryId").is(dto.getMain()));
        }
        if (dto.getSub() != null) {
            query.addCriteria(Criteria.where("subCategory.categoryId").is(dto.getSub()));
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            query.addCriteria(Criteria.where("productName").regex(dto.getName(), "i")); // 대소문자 무시
        }

        // 정렬 정보 적용
        if (dto.getPageable().getSort().isSorted()) {
            query.with(dto.getPageable().getSort());
        }

        long total = mongoTemplate.count(query, ProductRead.class);
        query.with(dto.getPageable());

        List<ProductRead> list = mongoTemplate.find(query, ProductRead.class);
        return new PageImpl<>(list, dto.getPageable(), total);
    }

    @Override
    public void updateRemainPieces(String fundingUuid, int remainPieces) {
        Query query = new Query(Criteria.where("fundingRead.fundingUuid").is(fundingUuid));
        Update update = new Update().set("fundingRead.remainPieces", remainPieces);
        mongoTemplate.updateFirst(query, update, ProductRead.class);
    }
}
