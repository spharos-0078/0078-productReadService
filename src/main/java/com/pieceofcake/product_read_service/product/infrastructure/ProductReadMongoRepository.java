package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.entity.ProductReadMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReadMongoRepository extends MongoRepository<ProductReadMongoEntity, String> {
}
