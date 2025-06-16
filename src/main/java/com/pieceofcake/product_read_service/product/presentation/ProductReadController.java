package com.pieceofcake.product_read_service.product.presentation;

import com.pieceofcake.product_read_service.common.entity.BaseResponseEntity;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.vo.out.GetProductDetailResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@RestController
public class ProductReadController {

    private final ProductReadServiceImpl productReadService;

    @GetMapping("/detail/{productUuid}")
    public BaseResponseEntity<GetProductDetailResponseVo> getProductDetail(@PathVariable String productUuid) {
        return new BaseResponseEntity<>(productReadService.getProductDetail(productUuid).toVo());
    }
}
