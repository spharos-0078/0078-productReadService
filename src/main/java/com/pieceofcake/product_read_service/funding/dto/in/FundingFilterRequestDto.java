package com.pieceofcake.product_read_service.funding.dto.in;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FundingFilterRequestDto {
    private String main;
    private String sub;
    private String name;
    Pageable pageable;
}
