package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class GetProductFilterRequestDto {
    private Pageable pageable;
    private Integer main;
    private Integer sub;
    private String name;
    private ProductStatus status;

    @Builder
    public GetProductFilterRequestDto(Pageable pageable, Integer main, Integer sub, String name, ProductStatus status) {
        this.pageable = pageable;
        this.main = main;
        this.sub = sub;
        this.name = name;
        this.status = status;
    }

    public static GetProductFilterRequestDto from(Pageable pageable, Integer main, Integer sub, String name, ProductStatus status) {
        return GetProductFilterRequestDto.builder()
                .pageable(pageable)
                .main(main)
                .sub(sub)
                .name(name)
                .status(status)
                .build();
    }
}
