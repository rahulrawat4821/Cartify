package com.rahul.cartify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.cartify.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
