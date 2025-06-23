package com.pieceofcake.product_read_service.piece.dto.out;

import com.pieceofcake.product_read_service.piece.vo.out.GetPieceUuidListResponseVo;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetPieceUuidListResponseDto {
    private List<String> pieceProductUuidList;
    private long page;
    private long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalPage;
    private long totalElements;

    @Builder
    public GetPieceUuidListResponseDto(List<String> pieceProductUuidList, long page, long size, boolean hasNext,
                                       boolean hasPrevious, long totalPage, long totalElements) {
        this.pieceProductUuidList = pieceProductUuidList;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
    }


    public static GetPieceUuidListResponseDto from(Page<ProductRead> page) {
        return GetPieceUuidListResponseDto.builder()
                .pieceProductUuidList(page.getContent().stream()
                        .map(product -> product.getPieceRead().getPieceProductUuid()).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    public GetPieceUuidListResponseVo toVo() {
        return GetPieceUuidListResponseVo.builder()
                .pieceProductUuidList(pieceProductUuidList)
                .page(page)
                .size(size)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .totalPage(totalPage)
                .totalElements(totalElements)
                .build();
    }
}
