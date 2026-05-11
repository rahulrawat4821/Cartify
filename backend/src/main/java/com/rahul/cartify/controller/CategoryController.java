package com.rahul.cartify.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rahul.cartify.dto.request.CategoryRequest;
import com.rahul.cartify.dto.response.CategoryResponse;
import com.rahul.cartify.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService
            categoryService;

    public CategoryController(
            CategoryService categoryService
    ) {
        this.categoryService =
                categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse>
    createCategory(
            @RequestBody CategoryRequest request
    ) {

        return ResponseEntity.ok(
                categoryService.createCategory(
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>>
    getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }
}