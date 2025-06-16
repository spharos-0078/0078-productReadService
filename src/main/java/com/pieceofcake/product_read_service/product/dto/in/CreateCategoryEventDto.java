package com.pieceofcake.product_read_service.product.dto.in;

import com.pieceofcake.product_read_service.kafka.event.CategoryReadEvent;
import com.pieceofcake.product_read_service.product.entity.CategoryReadEntity;
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

    public CategoryReadEntity toEntity(){
        return CategoryReadEntity.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
