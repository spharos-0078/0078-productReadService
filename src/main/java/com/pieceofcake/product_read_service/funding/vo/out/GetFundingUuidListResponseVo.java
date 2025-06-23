package com.pieceofcake.product_read_service.funding.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetFundingUuidListResponseVo {
    private List<String> fundingUuidList;
    private long page;
    private long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalPage;
    private long totalElements;

    @Builder
    public GetFundingUuidListResponseVo(
            List<String> fundingUuidList,
            long page,
            long size,
            boolean hasNext,
            boolean hasPrevious,
            long totalPage,
            long totalElements
    ){
        this.fundingUuidList = fundingUuidList;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
    }
}
