package com.pieceofcake.product_read_service.product.vo.out;

import lombok.Builder;
import lombok.Getter;

//공모: 상품 썸네일, 상품 이름, 상품 가격, 공모 마감일, 조각당 가격, 남은 조각 수
//조각: 상품 썸네일, 상품 이름,
//
//'상품 가치(가격.. 어떻게 계산..?)?, 시장가, 변동률' -> 흠
//남은 조각 수는 따로?

@Getter
public class GetProductOutlineResponseVo {
    private String productName;
    private String imageUrl;
//    private String productUuid;

    @Builder
    public GetProductOutlineResponseVo(String productName, String imageUrl) {
        this.productName = productName;
        this.imageUrl = imageUrl;
    }
}
