package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReadMongoRepository extends MongoRepository<ProductReadMongoEntity, String> {
    Optional<ProductReadMongoEntity> findByProductUuid(String productUuid);
    void deleteByProductUuid(String productUuid);
}
