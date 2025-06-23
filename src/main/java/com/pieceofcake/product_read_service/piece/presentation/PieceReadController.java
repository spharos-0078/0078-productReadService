package com.pieceofcake.product_read_service.piece.presentation;

import com.pieceofcake.product_read_service.common.entity.BaseResponseEntity;
import com.pieceofcake.product_read_service.piece.application.PieceReadServiceImpl;
import com.pieceofcake.product_read_service.piece.dto.in.GetPieceFilterRequestDto;
import com.pieceofcake.product_read_service.piece.entity.PieceSortBy;
import com.pieceofcake.product_read_service.piece.vo.out.GetPieceDetailResponseVo;
import com.pieceofcake.product_read_service.piece.vo.out.GetPieceUuidListResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/piece")
@RequiredArgsConstructor
@RestController
public class PieceReadController {
    private final PieceReadServiceImpl pieceReadService;

    @GetMapping("/list")
    public BaseResponseEntity<GetPieceUuidListResponseVo> getPieceFilterUuid(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String main,
            @RequestParam(required = false) String sub,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "ID") PieceSortBy sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(sortDirection, sortBy.getLabel());
        Pageable pageable = PageRequest.of(page, size, sort);

        return new BaseResponseEntity<>(pieceReadService.getPieceFilterUuid(
                        GetPieceFilterRequestDto.from(pageable, main, sub, name))
                .toVo());
    }

    @GetMapping("/list/{pieceProductUuid}")
    public BaseResponseEntity<GetPieceDetailResponseVo> getFundingDetail(
            @PathVariable("pieceProductUuid") String pieceProductUuid
    ) {
        return new BaseResponseEntity<>(pieceReadService.getPieceDetail(pieceProductUuid).toVo());
    }
}
