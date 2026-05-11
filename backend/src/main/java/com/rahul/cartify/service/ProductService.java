package com.rahul.cartify.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.rahul.cartify.dto.request.ProductRequest;
import com.rahul.cartify.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request, MultipartFile image);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            String keyword,
            int page,
            int size
    );
}