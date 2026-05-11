package com.rahul.cartify.service;

import java.util.List;

import com.rahul.cartify.dto.request.CategoryRequest;
import com.rahul.cartify.dto.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(
            CategoryRequest request
    );

    List<CategoryResponse> getAllCategories();
}