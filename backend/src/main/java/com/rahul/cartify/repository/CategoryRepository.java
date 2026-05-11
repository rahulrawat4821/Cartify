package com.rahul.cartify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.cartify.entity.Category;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

}