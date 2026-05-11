package com.rahul.cartify.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rahul.cartify.dto.request.CategoryRequest;
import com.rahul.cartify.dto.response.CategoryResponse;
import com.rahul.cartify.entity.Category;
import com.rahul.cartify.repository.CategoryRepository;
import com.rahul.cartify.service.CategoryService;

@Service
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository
            categoryRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository =
                categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(
            CategoryRequest request
    ) {

        Category category =
                new Category();

        category.setName(request.getName());

        Category savedCategory =
                categoryRepository.save(category);

        return new CategoryResponse(
                savedCategory.getId(),
                savedCategory.getName()
        );
    }

    @Override
    public List<CategoryResponse>
    getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category ->
                        new CategoryResponse(
                                category.getId(),
                                category.getName()
                        )
                )
                .toList();
    }
}