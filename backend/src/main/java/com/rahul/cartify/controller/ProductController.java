package com.rahul.cartify.controller;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

 @PostMapping
public ResponseEntity<ProductResponse> createProduct(

        @RequestParam("name") String name,

        @RequestParam("description") String description,

        @RequestParam("price") Double price,

        @RequestParam("image") MultipartFile image
) {

    ProductRequest request =
            new ProductRequest();

    request.setName(name);
    request.setDescription(description);
    request.setPrice(price);

    return ResponseEntity.ok(
            productService.createProduct(
                    request,
                    image
            )
    );
}

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                "Product deleted successfully"
        );
    }
}