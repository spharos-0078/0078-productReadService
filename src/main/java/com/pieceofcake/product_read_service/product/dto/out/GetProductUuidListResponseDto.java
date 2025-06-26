package com.pieceofcake.product_read_service.product.dto.out;

import com.pieceofcake.product_read_service.piece.vo.out.GetPieceUuidListResponseVo;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import com.pieceofcake.product_read_service.product.vo.out.GetProductUuidListResponseVo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetProductUuidListResponseDto {
    private List<String> productUuidList;
    private long page;
    private long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalPage;
    private long totalElements;

    @Builder
    public GetProductUuidListResponseDto(List<String> productUuidList, long page, long size, boolean hasNext,
                                         boolean hasPrevious, long totalPage, long totalElements) {
        this.productUuidList = productUuidList;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
    }


    public static GetProductUuidListResponseDto from(Page<ProductRead> page) {
        return GetProductUuidListResponseDto.builder()
                .productUuidList(page.getContent().stream()
                        .map(ProductRead::getProductUuid).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    public GetProductUuidListResponseVo toVo() {
        return GetProductUuidListResponseVo.builder()
                .productUuidList(productUuidList)
                .page(page)
                .size(size)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .totalPage(totalPage)
                .totalElements(totalElements)
                .build();
    }
}
