package com.bookstore.model.dto;

import java.util.List;

public class CartDto {
    private List<CartItemDto> cartItems;

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }
}
