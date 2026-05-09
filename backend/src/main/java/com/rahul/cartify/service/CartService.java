package com.rahul.cartify.service;

import com.rahul.cartify.dto.request.CartRequest;
import com.rahul.cartify.dto.request.UpdateCartRequest;
import com.rahul.cartify.dto.response.CartResponse;
import java.util.List;

public interface CartService {

    CartResponse addToCart(CartRequest request,String userEmail);
    List<CartResponse> getUserCart();
    void removeCartItem(Long cartItemId);
    CartResponse updateCartItem(Long cartItemId,UpdateCartRequest request);
}