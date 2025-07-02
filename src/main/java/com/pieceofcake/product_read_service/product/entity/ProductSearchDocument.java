package com.pieceofcake.product_read_service.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Document(indexName = "products")
@Setting(settingPath = "elasticsearch-settings.json")
public class ProductSearchDocument {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String productUuid;
    
    @Field(type = FieldType.Text, analyzer = "korean_analyzer")
    private String productName;
    
    @Field(type = FieldType.Long)
    private Long aiEstimatedPrice;
    
    @Field(type = FieldType.Text)
    private String aiEstimatedDescription;
    
    @Field(type = FieldType.Long)
    private Long purchasePrice;
    
    @Field(type = FieldType.Keyword)
    private String productStatus;
    
    @Field(type = FieldType.Text)
    private String storageLocation;
    
    @Field(type = FieldType.Text)
    private String description;
    
    @Field(type = FieldType.Nested)
    private List<ProductImageDocument> images;
    
    @Field(type = FieldType.Object)
    private CategoryDocument mainCategory;
    
    @Field(type = FieldType.Object)
    private CategoryDocument subCategory;
    
    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;
    
    @Builder
    public ProductSearchDocument(String id, String productUuid, String productName, 
                               Long aiEstimatedPrice, String aiEstimatedDescription,
                               Long purchasePrice, String productStatus, String storageLocation,
                               String description, List<ProductImageDocument> images,
                               CategoryDocument mainCategory, CategoryDocument subCategory,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productUuid = productUuid;
        this.productName = productName;
        this.aiEstimatedPrice = aiEstimatedPrice;
        this.aiEstimatedDescription = aiEstimatedDescription;
        this.purchasePrice = purchasePrice;
        this.productStatus = productStatus;
        this.storageLocation = storageLocation;
        this.description = description;
        this.images = images;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    @Getter
    @NoArgsConstructor
    public static class ProductImageDocument {
        @Field(type = FieldType.Keyword)
        private String imageUuid;
        
        @Field(type = FieldType.Text)
        private String imageUrl;
        
        @Field(type = FieldType.Integer)
        private Integer imageOrder;
        
        @Builder
        public ProductImageDocument(String imageUuid, String imageUrl, Integer imageOrder) {
            this.imageUuid = imageUuid;
            this.imageUrl = imageUrl;
            this.imageOrder = imageOrder;
        }
    }
    
    @Getter
    @NoArgsConstructor
    public static class CategoryDocument {
        @Field(type = FieldType.Integer)
        private Integer categoryId;
        
        @Field(type = FieldType.Text, analyzer = "korean_analyzer")
        private String categoryName;
        
        @Builder
        public CategoryDocument(Integer categoryId, String categoryName) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
        }
    }
} 