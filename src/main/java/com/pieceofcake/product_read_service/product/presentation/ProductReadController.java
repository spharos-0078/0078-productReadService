package com.pieceofcake.product_read_service.product.presentation;

import com.pieceofcake.product_read_service.common.entity.BaseResponseEntity;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import com.pieceofcake.product_read_service.product.vo.out.GetProductDetailResponseVo;
import com.pieceofcake.product_read_service.product.vo.out.GetProductUuidListResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@RestController
public class ProductReadController {

    private final ProductReadServiceImpl productReadService;

    @GetMapping("/list")
    public BaseResponseEntity<GetProductUuidListResponseVo> getProductFilterUuid(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer main,
            @RequestParam(required = false) Integer sub,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ProductStatus status
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        ;
        Pageable pageable = PageRequest.of(page, size, sort);

        return new BaseResponseEntity<>(productReadService.getProductFilterUuid(
                        GetProductFilterRequestDto.from(pageable, main, sub, name, status))
                .toVo());
    }

    @GetMapping("/list/{productUuid}")
    public BaseResponseEntity<GetProductDetailResponseVo> getProductDetail(@PathVariable String productUuid) {
        return new BaseResponseEntity<>(productReadService.getProductDetail(productUuid).toVo());
    }
}
