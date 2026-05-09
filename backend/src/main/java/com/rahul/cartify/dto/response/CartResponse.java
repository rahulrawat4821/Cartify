package com.rahul.cartify.dto.response;

public class CartResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private String message;

    public CartResponse() {
    }

    // for add to cart message
    public CartResponse(String message) {
        this.message = message;
    }

    // for get cart items
    public CartResponse(
            Long cartItemId,
            Long productId,
            String productName,
            Double price,
            Integer quantity
    ) {

        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}