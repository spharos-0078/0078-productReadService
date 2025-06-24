package com.pieceofcake.product_read_service.funding.dto.in;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FundingFilterRequestDto {
    private Integer main;
    private Integer sub;
    private String name;
    Pageable pageable;
}
