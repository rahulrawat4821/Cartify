package com.rahul.cartify.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rahul.cartify.dto.request.ProductRequest;
import com.rahul.cartify.dto.response.ProductResponse;
import com.rahul.cartify.entity.Category;
import com.rahul.cartify.entity.Product;
import com.rahul.cartify.repository.CategoryRepository;
import com.rahul.cartify.repository.ProductRepository;
import com.rahul.cartify.service.ImageUploadService;
import com.rahul.cartify.service.ProductService;

import static com.rahul.cartify.specification.ProductSpecification.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageUploadService imageUploadService;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ImageUploadService imageUploadService,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.imageUploadService = imageUploadService;
        this.categoryRepository = categoryRepository;
    }

    // ───────────────────────────────────────────────────
    // helper method
    // ───────────────────────────────────────────────────

    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStock(),

                product.getCategory() != null
                        ? product.getCategory().getId()
                        : null,

                product.getCategory() != null
                        ? product.getCategory().getName()
                        : null
        );
    }

    // ───────────────────────────────────────────────────
    // get all + filter + pagination
    // ───────────────────────────────────────────────────

    @Override
    public Page<ProductResponse> getAllProducts(
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            String keyword,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Specification<Product> spec =
                Specification.where(
                        hasCategory(categoryId)
                )
                .and(hasMinPrice(minPrice))
                .and(hasMaxPrice(maxPrice))
                .and(hasKeyword(keyword));

        return productRepository
                .findAll(spec, pageable)
                .map(this::mapToResponse);
    }

    // ───────────────────────────────────────────────────
    // get product by id
    // ───────────────────────────────────────────────────

    @Override
    public ProductResponse getProductById(Long id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                ));

        return mapToResponse(product);
    }

    // ───────────────────────────────────────────────────
    // create product
    // ───────────────────────────────────────────────────

    @Override
    public ProductResponse createProduct(
            ProductRequest request,
            MultipartFile image
    ) {

        String imageUrl =
                imageUploadService.uploadImage(image);

        Category category =
                categoryRepository.findById(
                        request.getCategoryId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        ));

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(imageUrl);
        product.setCategory(category);

        Product savedProduct =
                productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    // ───────────────────────────────────────────────────
    // update product
    // ───────────────────────────────────────────────────

    @Override
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request
    ) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                ));

        Category category =
                categoryRepository.findById(
                        request.getCategoryId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        ));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    // ───────────────────────────────────────────────────
    // delete product
    // ───────────────────────────────────────────────────

    @Override
    public void deleteProduct(Long id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                ));

        productRepository.delete(product);
    }
}

