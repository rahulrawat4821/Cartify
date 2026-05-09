package com.rahul.cartify.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rahul.cartify.dto.request.CartRequest;
import com.rahul.cartify.dto.request.UpdateCartRequest;
import com.rahul.cartify.dto.response.CartResponse;
import com.rahul.cartify.entity.Cart;
import com.rahul.cartify.entity.CartItem;
import com.rahul.cartify.entity.Product;
import com.rahul.cartify.entity.User;
import com.rahul.cartify.repository.CartItemRepository;
import com.rahul.cartify.repository.CartRepository;
import com.rahul.cartify.repository.ProductRepository;
import com.rahul.cartify.repository.UserRepository;
import com.rahul.cartify.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartResponse addToCart(
            CartRequest request,
            String userEmail
    ) {

        // find user
        User user =
                userRepository.findByEmail(userEmail);

        // find cart
        Optional<Cart> optionalCart =
                cartRepository.findByUser(user);

        Cart cart;

        // create cart if not exists
        if (optionalCart.isPresent()) {

            cart = optionalCart.get();

        } else {

            cart = new Cart();

            cart.setUser(user);

            cart = cartRepository.save(cart);
        }

        // find product
        Product product =
                productRepository.findById(
                        request.getProductId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        )
                );

        // create cart item
        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);

        cartItem.setProduct(product);

        cartItem.setQuantity(
                request.getQuantity()
        );

        cartItemRepository.save(cartItem);

        return new CartResponse(
                "Product added to cart"
        );
    }

    @Override
    public List<CartResponse> getUserCart() {

        // get logged-in user email
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        // find user
        User user =
                userRepository.findByEmail(email);

        // find cart
        Cart cart =
                cartRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart not found"
                                )
                        );

        // get cart items
        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);

        // convert to response
        return cartItems.stream()
                .map(cartItem -> {

                    CartResponse response =
                            new CartResponse();

                    response.setCartItemId(
                            cartItem.getId()
                    );

                    response.setProductId(
                            cartItem.getProduct().getId()
                    );

                    response.setProductName(
                            cartItem.getProduct().getName()
                    );

                    response.setPrice(
                            cartItem.getProduct().getPrice()
                    );

                    response.setQuantity(
                            cartItem.getQuantity()
                    );

                    return response;
                })
                .toList();
    }

    @Override
public void removeCartItem(Long cartItemId) {

    CartItem cartItem =
            cartItemRepository.findById(cartItemId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Cart item not found"
                            )
                    );

    cartItemRepository.delete(cartItem);
}


@Override
public CartResponse updateCartItem(
        Long cartItemId,
        UpdateCartRequest request
) {

    CartItem cartItem =
            cartItemRepository.findById(cartItemId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Cart item not found"
                            )
                    );

    cartItem.setQuantity(
            request.getQuantity()
    );

    CartItem updatedItem =
            cartItemRepository.save(cartItem);

    return new CartResponse(
            updatedItem.getId(),
            updatedItem.getProduct().getId(),
            updatedItem.getProduct().getName(),
            updatedItem.getProduct().getPrice(),
            updatedItem.getQuantity()
    );
}
}