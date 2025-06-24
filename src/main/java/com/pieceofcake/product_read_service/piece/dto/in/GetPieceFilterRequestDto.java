package com.pieceofcake.product_read_service.piece.dto.in;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class GetPieceFilterRequestDto {
    private Pageable pageable;
    private Integer main;
    private Integer sub;
    private String name;

    @Builder
    public GetPieceFilterRequestDto(Pageable pageable, Integer main, Integer sub, String name) {
        this.pageable = pageable;
        this.main = main;
        this.sub = sub;
        this.name = name;
    }

    public static GetPieceFilterRequestDto from(Pageable pageable, Integer main, Integer sub, String name) {
        return GetPieceFilterRequestDto.builder()
                .pageable(pageable)
                .main(main)
                .sub(sub)
                .name(name)
                .build();
    }
}
