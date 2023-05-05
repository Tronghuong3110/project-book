package com.bookstore.service;

import com.bookstore.model.dto.CartItemDto;

public interface ICartService {
    CartItemDto save(Long bookId, CartItemDto cartItem);
    void deleteCartItem(Long id);
}
