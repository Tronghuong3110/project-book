package com.bookstore.service;

import com.bookstore.model.dto.CartItemDto;
import java.util.*;

public interface ICartService {
    CartItemDto save(Long bookId, CartItemDto cartItem);
    String deleteCartItem(Long id);
    List<CartItemDto> getListCartItem(Integer status);
    Long countCartItem();
}
