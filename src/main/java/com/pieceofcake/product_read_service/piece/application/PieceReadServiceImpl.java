package com.pieceofcake.product_read_service.piece.application;

import com.pieceofcake.product_read_service.common.entity.BaseResponseStatus;
import com.pieceofcake.product_read_service.common.exception.BaseException;
import com.pieceofcake.product_read_service.piece.dto.in.CreateBatchEventDto;
import com.pieceofcake.product_read_service.piece.dto.in.CreatePieceEventDto;
import com.pieceofcake.product_read_service.piece.dto.in.GetPieceFilterRequestDto;
import com.pieceofcake.product_read_service.piece.dto.out.GetPieceDetailResponseDto;
import com.pieceofcake.product_read_service.piece.dto.out.GetPieceUuidListResponseDto;
import com.pieceofcake.product_read_service.piece.entity.PieceStatus;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PieceReadServiceImpl implements PieceReadService {

    private final ProductReadMongoRepository productReadMongoRepository;

    @Override
    public void createPieceRead(CreatePieceEventDto createPieceEventDto) {
        ProductRead product = productReadMongoRepository.findByProductUuid(createPieceEventDto.getProductUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        product.getFundingRead().updateFundingReadStatus("COMPLETED");
        product.createPieceRead(createPieceEventDto.toEntity());
        productReadMongoRepository.save(product);
    }

    @Override
    public void createPieceBatchRead(CreateBatchEventDto createBatchEventDto) {
        ProductRead product = productReadMongoRepository
                .findByPieceRead_PieceProductUuid(createBatchEventDto.getPieceProductUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PIECE_PRODUCT));

        product.createPieceRead(createBatchEventDto.toEntity(product.getPieceRead()));
        productReadMongoRepository.save(product);
    }

    @Override
    public GetPieceUuidListResponseDto getPieceFilterUuid(GetPieceFilterRequestDto getPieceFilterRequestDto) {
        return GetPieceUuidListResponseDto.from(productReadMongoRepository.searchPieceProductWithFilters(getPieceFilterRequestDto));
    }

    @Override
    public GetPieceDetailResponseDto getPieceDetail(String pieceProductUuid) {
        return GetPieceDetailResponseDto.from(productReadMongoRepository.findByPieceRead_PieceProductUuid(pieceProductUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PIECE_PRODUCT)));
    }

    @Override
    public void updatePieceStatus(String pieceProductUuid, PieceStatus pieceStatus) {
        ProductRead product = productReadMongoRepository.findByPieceRead_PieceProductUuid(pieceProductUuid)
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.getPieceRead().updateStatus(pieceStatus);

        productReadMongoRepository.save(product);
    }
}
