package com.pieceofcake.product_read_service.funding.dto.out;

import com.pieceofcake.product_read_service.funding.vo.out.GetFundingUuidListResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class GetFundingUuidListResponseDto {
    private List<String> fundingUuidList;
    private long page;
    private long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalPage;
    private long totalElements;

    @Builder
    public GetFundingUuidListResponseDto(
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

    public static GetFundingUuidListResponseDto from(Page<String> page){
        return GetFundingUuidListResponseDto.builder()
                .fundingUuidList(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    public GetFundingUuidListResponseVo toVo(){
        return GetFundingUuidListResponseVo.builder()
                .fundingUuidList(fundingUuidList)
                .page(page)
                .size(size)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .totalPage(totalPage)
                .totalElements(totalElements)
                .build();
    }
}
