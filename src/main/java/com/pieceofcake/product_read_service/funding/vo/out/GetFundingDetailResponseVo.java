package com.pieceofcake.product_read_service.funding.vo.out;

import com.pieceofcake.product_read_service.product.vo.out.GetCategoryResponseVo;
import com.pieceofcake.product_read_service.product.vo.out.GetProductImageResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetFundingDetailResponseVo {
    private String productUuid;
    private GetCategoryResponseVo mainCategory;
    private GetCategoryResponseVo subCategory;
    private String productName;
    private String description;
    private Long aiEstimatedPrice;
    private String aiEstimatedDescription;
    private List<GetProductImageResponseVo> images;
    private GetFundingResponseVo funding;

    @Builder
    public GetFundingDetailResponseVo(
            String productUuid,
            GetCategoryResponseVo mainCategory,
            GetCategoryResponseVo subCategory,
            String productName,
            String description,
            Long aiEstimatedPrice,
            String aiEstimatedDescription,
            List<GetProductImageResponseVo> images,
            GetFundingResponseVo funding
    ){
        this.productUuid = productUuid;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.productName = productName;
        this.description = description;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.images = images;
        this.funding = funding;
    }
}
