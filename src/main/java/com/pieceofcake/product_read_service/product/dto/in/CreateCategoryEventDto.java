package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.kafka.event.CategoryReadEvent;
import com.pieceofcake.product_read_service.product.entity.CategoryRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CreateCategoryEventDto {
    private Integer categoryId;
    private String categoryName;

    @Builder
    public CreateCategoryEventDto(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static CreateCategoryEventDto from(CategoryReadEvent categoryEvent) {
        return CreateCategoryEventDto.builder()
                .categoryId(categoryEvent.getCategoryId())
                .categoryName(categoryEvent.getCategoryName())
                .build();
    }

    public CategoryRead toEntity(){
        return CategoryRead.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
