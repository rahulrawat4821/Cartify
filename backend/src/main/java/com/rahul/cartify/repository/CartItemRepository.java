package com.rahul.cartify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.cartify.entity.Cart;
import com.rahul.cartify.entity.CartItem;
import java.util.List;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

            List<CartItem> findByCart(Cart cart);

}