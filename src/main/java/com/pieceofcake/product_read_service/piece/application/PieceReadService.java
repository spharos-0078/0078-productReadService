package com.pieceofcake.product_read_service.piece.application;

import com.pieceofcake.product_read_service.piece.dto.in.CreateBatchEventDto;
import com.pieceofcake.product_read_service.piece.dto.in.CreatePieceEventDto;
import com.pieceofcake.product_read_service.piece.dto.in.GetPieceFilterRequestDto;
import com.pieceofcake.product_read_service.piece.dto.out.GetPieceDetailResponseDto;
import com.pieceofcake.product_read_service.piece.dto.out.GetPieceUuidListResponseDto;

public interface PieceReadService {
    void createPieceRead(CreatePieceEventDto createPieceEventDto);

    void createPieceBatchRead(CreateBatchEventDto createBatchEventDto);

    GetPieceUuidListResponseDto getPieceFilterUuid(GetPieceFilterRequestDto from);

    GetPieceDetailResponseDto getPieceDetail(String pieceProductUuid);
}
