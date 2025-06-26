package com.pieceofcake.product_read_service.product.infrastructure;

import com.pieceofcake.product_read_service.funding.infrastructure.FundingReadMongoRepository;
import com.pieceofcake.product_read_service.piece.infrastructure.PieceReadMongoRepository;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReadMongoRepository extends MongoRepository<ProductRead, String>,
        FundingReadMongoRepository, PieceReadMongoRepository, ProductReadCustomMongoRepository {
    /*
     * funding 넣을 때 findByProductUuid 조회 후 반영함
     * 조회 api 수정 시 해당 메서드는 남겨주세요
     * */
    Optional<ProductRead> findByProductUuid(String productUuid);

    void deleteByProductUuid(String productUuid);

    Optional<ProductRead> findByFundingRead_FundingUuid(String fundingUuid);

    Optional<ProductRead> findByPieceRead_PieceProductUuid(String pieceProductUuid);
}
