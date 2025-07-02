package com.pieceofcake.product_read_service.product.presentation;

import com.pieceofcake.product_read_service.common.entity.BaseResponseEntity;
import com.pieceofcake.product_read_service.product.application.ProductReadServiceImpl;
import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.entity.ProductStatus;
import com.pieceofcake.product_read_service.product.vo.out.GetProductDetailResponseVo;
import com.pieceofcake.product_read_service.product.vo.out.GetProductUuidListResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Slf4j
@Validated
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"https://your-frontend-domain.com"}, maxAge = 3600)
public class ProductReadController {

    private final ProductReadServiceImpl productReadService;

    @GetMapping("/list")
    public BaseResponseEntity<GetProductUuidListResponseVo> getProductFilterUuid(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(required = false) Integer main,
            @RequestParam(required = false) Integer sub,
            @RequestParam(required = false) @Size(max = 50) String name,
            @RequestParam(required = false) ProductStatus status
    ) {
        log.info("Product list request - page: {}, size: {}, main: {}, sub: {}, name: {}, status: {}", 
                page, size, main, sub, name, status);
        
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        return new BaseResponseEntity<>(productReadService.getProductFilterUuid(
                        GetProductFilterRequestDto.from(pageable, main, sub, name, status))
                .toVo());
    }

    @GetMapping("/list/{productUuid}")
    public BaseResponseEntity<GetProductDetailResponseVo> getProductDetail(
            @PathVariable @Pattern(regexp = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$") String productUuid) {
        log.info("Product detail request - productUuid: {}", productUuid);
        return new BaseResponseEntity<>(productReadService.getProductDetail(productUuid).toVo());
    }
}
