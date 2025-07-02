package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, String> {
    Optional<ProductSearchDocument> findByProductUuid(String productUuid);
    void deleteByProductUuid(String productUuid);
} 