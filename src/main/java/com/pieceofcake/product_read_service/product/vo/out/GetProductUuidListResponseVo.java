package com.pieceofcake.product_read_service.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetProductUuidListResponseVo {
    private List<String> productUuidList;
    private long page;
    private long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalPage;
    private long totalElements;

    @Builder
    public GetProductUuidListResponseVo(List<String> productUuidList, long page, long size, boolean hasNext,
                                      boolean hasPrevious, long totalPage, long totalElements) {
        this.productUuidList = productUuidList;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
    }
}
