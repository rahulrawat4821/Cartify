package com.rahul.cartify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.rahul.cartify.dto.request.CartRequest;
import com.rahul.cartify.dto.request.UpdateCartRequest;
import com.rahul.cartify.dto.response.CartResponse;
import com.rahul.cartify.service.CartService;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(
            CartService cartService
    ) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @RequestBody CartRequest request,
            Authentication authentication
    ) {

        // get logged-in user email
        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.addToCart(
                        request,
                        email
                )
        );
    }


    @GetMapping
public ResponseEntity<List<CartResponse>> getUserCart() {

    return ResponseEntity.ok(
            cartService.getUserCart()
    );
}

@DeleteMapping("/{cartItemId}")
public ResponseEntity<String> removeCartItem(
        @PathVariable Long cartItemId
) {

    cartService.removeCartItem(cartItemId);

    return ResponseEntity.ok(
            "Cart item removed successfully"
    );
}

@PutMapping("/{cartItemId}")
public ResponseEntity<CartResponse> updateCartQuantity(
        @PathVariable Long cartItemId,
        @RequestBody UpdateCartRequest request
) {

    return ResponseEntity.ok(
            cartService.updateCartItem(
                    cartItemId,
                    request
            )
    );
}
}