package com.pieceofcake.product_read_service.product.application;

import com.pieceofcake.product_read_service.common.entity.BaseResponseStatus;
import com.pieceofcake.product_read_service.common.exception.BaseException;
import com.pieceofcake.product_read_service.product.dto.in.CreateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.GetProductFilterRequestDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductEventDto;
import com.pieceofcake.product_read_service.product.dto.in.UpdateProductStatusEventDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductDetailResponseDto;
import com.pieceofcake.product_read_service.product.dto.out.GetProductUuidListResponseDto;
import com.pieceofcake.product_read_service.product.entity.ProductRead;
import com.pieceofcake.product_read_service.product.infrastructure.ProductReadMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductReadServiceImpl implements ProductReadService {

    private final ProductReadMongoRepository productReadMongoRepository;

    @Override
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void createProductRead(CreateProductEventDto createProductEventDto) {
        log.info("Creating product read for UUID: {}", createProductEventDto.getProductUuid());
        
        try {
            // 중복 체크
            if (productReadMongoRepository.findByProductUuid(createProductEventDto.getProductUuid()).isPresent()) {
                log.warn("Product already exists: {}", createProductEventDto.getProductUuid());
                return; // 멱등성 보장
            }
            
            ProductRead productRead = createProductEventDto.toEntity();
            productReadMongoRepository.save(productRead);
            
            log.info("Product created successfully: {}", createProductEventDto.getProductUuid());
            
        } catch (Exception e) {
            log.error("Failed to create product: {}", createProductEventDto.getProductUuid(), e);
            throw new BaseException(BaseResponseStatus.FAILED_TO_SAVE);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#updateProductEventDto.productUuid")
    public void updateProductRead(UpdateProductEventDto updateProductEventDto) {
        log.info("Updating product read for UUID: {}", updateProductEventDto.getProductUuid());
        
        try {
            ProductRead existingProduct = productReadMongoRepository.findByProductUuid(updateProductEventDto.getProductUuid())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

            ProductRead updatedProduct = updateProductEventDto.toEntity(existingProduct);
            productReadMongoRepository.save(updatedProduct);
            
            log.info("Product updated successfully: {}", updateProductEventDto.getProductUuid());
            
        } catch (Exception e) {
            log.error("Failed to update product: {}", updateProductEventDto.getProductUuid(), e);
            throw new BaseException(BaseResponseStatus.FAILED_TO_SAVE);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#productUuid")
    public void deleteProductRead(String productUuid) {
        log.info("Deleting product read for UUID: {}", productUuid);
        
        try {
            productReadMongoRepository.deleteByProductUuid(productUuid);
            
            log.info("Product deleted successfully: {}", productUuid);
            
        } catch (Exception e) {
            log.error("Failed to delete product: {}", productUuid, e);
            throw new BaseException(BaseResponseStatus.FAILED_TO_SAVE);
        }
    }

    @Override
    @Cacheable(value = "products", key = "#productUuid", unless = "#result == null")
    public GetProductDetailResponseDto getProductDetail(String productUuid) {
        log.info("Getting product detail for UUID: {}", productUuid);
        
        try {
            ProductRead productRead = productReadMongoRepository.findByProductUuid(productUuid)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

            return GetProductDetailResponseDto.from(productRead);
            
        } catch (Exception e) {
            log.error("Failed to get product detail: {}", productUuid, e);
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }
    }

    @Override
    @Cacheable(value = "productLists", key = "#getProductFilterRequestDto.hashCode()", unless = "#result == null")
    public GetProductUuidListResponseDto getProductFilterUuid(GetProductFilterRequestDto getProductFilterRequestDto) {
        log.info("Getting product list with filters: {}", getProductFilterRequestDto);
        
        try {
            return GetProductUuidListResponseDto.from(productReadMongoRepository.searchProductWithFilters(getProductFilterRequestDto));
            
        } catch (Exception e) {
            log.error("Failed to get product list with filters", e);
            throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#updateProductStatusEventDto.productUuid")
    public void updateProductStatus(UpdateProductStatusEventDto updateProductStatusEventDto) {
        log.info("Updating product status for UUID: {} to {}", 
                updateProductStatusEventDto.getProductUuid(), updateProductStatusEventDto.getProductStatus());
        
        try {
            ProductRead product = productReadMongoRepository.findByProductUuid(updateProductStatusEventDto.getProductUuid())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

            // 상태 변경 전 원본 상태 백업 (보상 로직용)
            String originalStatus = product.getProductStatus();
            
            product.updateProductStatus(updateProductStatusEventDto.getProductStatus());
            productReadMongoRepository.save(product);
            
            log.info("Product status updated successfully: {} -> {}", 
                    originalStatus, updateProductStatusEventDto.getProductStatus());
            
        } catch (Exception e) {
            log.error("Failed to update product status: {}", updateProductStatusEventDto.getProductUuid(), e);
            throw new BaseException(BaseResponseStatus.FAILED_TO_SAVE);
        }
    }

    /**
     * 보상 로직: 상품 생성 실패 시 삭제
     */
    public void compensateCreateProduct(String productUuid) {
        log.info("Executing compensation for create product: {}", productUuid);
        try {
            productReadMongoRepository.deleteByProductUuid(productUuid);
            log.info("Compensation completed for create product: {}", productUuid);
        } catch (Exception e) {
            log.error("Compensation failed for create product: {}", productUuid, e);
        }
    }

    /**
     * 보상 로직: 상품 업데이트 실패 시 원본 복원
     */
    public void compensateUpdateProduct(ProductRead originalProduct) {
        log.info("Executing compensation for update product: {}", originalProduct.getProductUuid());
        try {
            productReadMongoRepository.save(originalProduct);
            log.info("Compensation completed for update product: {}", originalProduct.getProductUuid());
        } catch (Exception e) {
            log.error("Compensation failed for update product: {}", originalProduct.getProductUuid(), e);
        }
    }

    /**
     * 보상 로직: 상품 삭제 실패 시 원본 복원
     */
    public void compensateDeleteProduct(ProductRead originalProduct) {
        log.info("Executing compensation for delete product: {}", originalProduct.getProductUuid());
        try {
            productReadMongoRepository.save(originalProduct);
            log.info("Compensation completed for delete product: {}", originalProduct.getProductUuid());
        } catch (Exception e) {
            log.error("Compensation failed for delete product: {}", originalProduct.getProductUuid(), e);
        }
    }
}
