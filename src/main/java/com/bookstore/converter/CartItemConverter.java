package com.bookstore.converter;

import com.bookstore.model.dto.BookDto;
import com.bookstore.model.dto.CartItemDto;
import com.bookstore.model.entity.BookEntity;
import com.bookstore.model.entity.CartItemEntity;

public class CartItemConverter {

    public static CartItemEntity toEntity(CartItemDto cartItemDto) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setQuantity(cartItemDto.getQuantity());
        cartItemEntity.setStatus(0); // cho biet sach moi chi duoc them vao gio hang, chua duoc dat
        return cartItemEntity;
    }

    public static CartItemDto toDto(CartItemEntity cartItemEntity) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItemEntity.getId());
        cartItemDto.setBook(toBookDto(cartItemEntity.getBook()));
        cartItemDto.setQuantity(cartItemEntity.getQuantity());
        cartItemDto.setTotalPrice(cartItemEntity.getTotalPrice());
        return cartItemDto;
    }

    private static BookDto toBookDto(BookEntity bookEntity) {
        return BookConverter.toDto(bookEntity);
    }
}
