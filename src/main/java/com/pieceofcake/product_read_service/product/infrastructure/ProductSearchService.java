package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductSearchDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public Page<ProductSearchDocument> searchProducts(GetProductFilterRequestDto dto) {
        try {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            // 메인 카테고리 필터
            if (dto.getMain() != null) {
                boolQuery.must(QueryBuilders.termQuery("mainCategory.categoryId", dto.getMain()));
            }

            // 서브 카테고리 필터
            if (dto.getSub() != null) {
                boolQuery.must(QueryBuilders.termQuery("subCategory.categoryId", dto.getSub()));
            }

            // 상품명 검색 - 전문 검색 (Full-text search)
            if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
                String searchTerm = dto.getName().trim();
                
                // 다중 필드 검색 (상품명 + 설명)
                BoolQueryBuilder nameQuery = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("productName", searchTerm)
                        .fuzziness(org.elasticsearch.common.unit.Fuzziness.AUTO)
                        .boost(2.0f))
                    .should(QueryBuilders.matchQuery("description", searchTerm)
                        .fuzziness(org.elasticsearch.common.unit.Fuzziness.AUTO)
                        .boost(1.0f))
                    .should(QueryBuilders.matchQuery("mainCategory.categoryName", searchTerm)
                        .fuzziness(org.elasticsearch.common.unit.Fuzziness.AUTO)
                        .boost(1.5f))
                    .should(QueryBuilders.matchQuery("subCategory.categoryName", searchTerm)
                        .fuzziness(org.elasticsearch.common.unit.Fuzziness.AUTO)
                        .boost(1.5f));
                
                boolQuery.must(nameQuery);
            }

            // 상태 필터
            if (dto.getStatus() != null) {
                boolQuery.must(QueryBuilders.termQuery("productStatus", dto.getStatus().name()));
            }

            // 검색 쿼리 빌드
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withSort(SortBuilders.fieldSort("createdAt").order(SortOrder.DESC))
                .withPageable(dto.getPageable());

            NativeSearchQuery searchQuery = queryBuilder.build();

            // 검색 실행
            SearchHits<ProductSearchDocument> searchHits = elasticsearchOperations.search(searchQuery, ProductSearchDocument.class);

            // 결과 변환
            List<ProductSearchDocument> products = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

            return new PageImpl<>(products, dto.getPageable(), searchHits.getTotalHits());

        } catch (Exception e) {
            log.error("Elasticsearch search failed", e);
            throw new RuntimeException("검색 중 오류가 발생했습니다.", e);
        }
    }

    public void indexProduct(ProductSearchDocument product) {
        try {
            elasticsearchOperations.save(product);
            log.info("Product indexed successfully: {}", product.getProductUuid());
        } catch (Exception e) {
            log.error("Failed to index product: {}", product.getProductUuid(), e);
            throw new RuntimeException("상품 인덱싱에 실패했습니다.", e);
        }
    }

    public void deleteProduct(String productUuid) {
        try {
            elasticsearchOperations.delete(productUuid, ProductSearchDocument.class);
            log.info("Product deleted from index: {}", productUuid);
        } catch (Exception e) {
            log.error("Failed to delete product from index: {}", productUuid, e);
            throw new RuntimeException("상품 인덱스 삭제에 실패했습니다.", e);
        }
    }
} 