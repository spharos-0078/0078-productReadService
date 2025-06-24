package com.pieceofcake.product_read_service.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "product_category_read")
public class CategoryRead {
    @Id
    private String id;
    private Integer categoryId;
    private String categoryName;

    @Builder
    public CategoryRead(String id, Integer categoryId, String categoryName) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
