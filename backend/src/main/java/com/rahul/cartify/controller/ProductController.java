package com.rahul.cartify.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rahul.cartify.dto.request.ProductRequest;
import com.rahul.cartify.dto.response.ProductResponse;
import com.rahul.cartify.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ── get all + filter + paginate (single endpoint) ──
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(

            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String keyword,

            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                productService.getAllProducts(
                        categoryId, minPrice,
                        maxPrice, keyword,
                        page, size
                )
        );
    }

    // ── get by id ──────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id));
    }

    // ── create ─────────────────────────────────────────

@PostMapping(consumes = "multipart/form-data")
public ResponseEntity<ProductResponse> createProduct(

        @RequestParam("name") String name,

        @RequestParam("description") String description,

        @RequestParam("price") Double price,

        @RequestParam("categoryId") Long categoryId,

        @RequestParam("image") MultipartFile image,

        @RequestParam("stock") Integer stock
) {

    ProductRequest request = new ProductRequest();

    request.setName(name);
    request.setDescription(description);
    request.setPrice(price);
    request.setCategoryId(categoryId);
    request.setStock(stock);

    return ResponseEntity.ok(
            productService.createProduct(
                    request,
                    image
            )
    );
}



    // ── update ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request));
    }

    // ── delete ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}