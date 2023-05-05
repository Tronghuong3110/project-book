package com.bookstore.controller.user;

import com.bookstore.model.dto.CartItemDto;
import com.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class CartApi {

    @Autowired
    private ICartService cartService;

    @PostMapping("/cart/{bookId}")
    public ResponseEntity<?> addTocart(@PathVariable("bookId") String bookId, @RequestBody CartItemDto cartItemDto) {
        CartItemDto cartItem = cartService.save(Long.valueOf(bookId), cartItemDto);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") String cartItemId) {
        cartService.deleteCartItem(Long.valueOf(cartItemId));
        return ResponseEntity.ok("Xoa sản phẩm thành công");
    }
}
