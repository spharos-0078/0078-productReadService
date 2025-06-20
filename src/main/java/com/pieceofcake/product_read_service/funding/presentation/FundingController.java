package com.pieceofcake.product_read_service.funding.presentation;

import com.pieceofcake.product_read_service.common.entity.BaseResponseEntity;
import com.pieceofcake.product_read_service.funding.application.FundingReadService;
import com.pieceofcake.product_read_service.funding.dto.in.FundingFilterRequestDto;
import com.pieceofcake.product_read_service.funding.entity.SortBy;
import com.pieceofcake.product_read_service.funding.vo.out.GetFundingDetailResponseVo;
import com.pieceofcake.product_read_service.funding.vo.out.GetFundingResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/funding")
@RequiredArgsConstructor
@RestController
public class FundingController {

    private final FundingReadService fundingReadService;

    @GetMapping("/list")
    public BaseResponseEntity<Page<String>> getFundingFilterUuid(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String main,
            @RequestParam(required = false) String sub,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "ID") SortBy sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(sortDirection, sortBy.getLabel());
        Pageable pageable = PageRequest.of(page, size, sort);
        return new BaseResponseEntity<>(fundingReadService.getFundingFilterUuid(FundingFilterRequestDto.builder()
                .main(main)
                .sub(sub)
                .name(name)
                .pageable(pageable)
                .build()
        ));
    }

    @GetMapping("/list/{fundingUuid}")
    public BaseResponseEntity<GetFundingDetailResponseVo> getFundingDetail(
            @PathVariable("fundingUuid") String fundingUuid
    ){
        return new BaseResponseEntity<>(fundingReadService.getFundingDetail(fundingUuid).toVo());
    }
}
