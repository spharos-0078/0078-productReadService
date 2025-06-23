package com.pieceofcake.product_read_service.piece.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPieceUuidListResponseVo {
    private List<String> pieceProductUuidList;
    private long page;
    private long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalPage;
    private long totalElements;

    @Builder
    public GetPieceUuidListResponseVo(List<String> pieceProductUuidList, long page, long size, boolean hasNext,
                                      boolean hasPrevious, long totalPage, long totalElements) {
        this.pieceProductUuidList = pieceProductUuidList;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
    }
}
