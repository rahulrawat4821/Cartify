package com.rahul.cartify.specification;

import org.springframework.data.jpa.domain.Specification;

import com.rahul.cartify.entity.Product;

public class ProductSpecification {

    public static Specification<Product>
    hasCategory(Long categoryId) {

        return (root, query, cb) ->

                categoryId == null
                        ? null
                        : cb.equal(
                                root.get("category").get("id"),
                                categoryId
                        );
    }

    public static Specification<Product>
    hasMinPrice(Double minPrice) {

        return (root, query, cb) ->

                minPrice == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice
                        );
    }

    public static Specification<Product>
    hasMaxPrice(Double maxPrice) {

        return (root, query, cb) ->

                maxPrice == null
                        ? null
                        : cb.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice
                        );
    }

    public static Specification<Product>
    hasKeyword(String keyword) {

        return (root, query, cb) ->

                keyword == null
                        ? null
                        : cb.like(
                                cb.lower(root.get("name")),
                                "%" + keyword.toLowerCase() + "%"
                        );
    }
}