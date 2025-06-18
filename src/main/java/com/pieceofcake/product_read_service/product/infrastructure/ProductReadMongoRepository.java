package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReadMongoRepository extends MongoRepository<ProductReadMongoEntity, String> {
    /*
    * funding 넣을 때 findByProductUuid 조회 후 반영함
    * 조회 api 수정 시 해당 메서드는 남겨주세요
    * */
    Optional<ProductReadMongoEntity> findByProductUuid(String productUuid);
    void deleteByProductUuid(String productUuid);
}
