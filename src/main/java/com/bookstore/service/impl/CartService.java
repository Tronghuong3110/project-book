package com.bookstore.service.impl;

import com.bookstore.Exception.RescourceNotFoundException;
import com.bookstore.converter.CartItemConverter;
import com.bookstore.model.dto.CartItemDto;
import com.bookstore.model.dto.Myuser;
import com.bookstore.model.entity.BookEntity;
import com.bookstore.model.entity.CartEntity;
import com.bookstore.model.entity.CartItemEntity;
import com.bookstore.model.entity.UserEntity;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.CartRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItemDto save(Long bookId, CartItemDto cartItem) {
        if(cartItem.getQuantity() <= 0) {
            return null;
        }
        // get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userRepository.findByUserName(userName).get();
        CartEntity cartEntity = user.getCart(); // get cart of user
        // TH user chua co gio hang, tao cho user 1 cart
        if(cartEntity == null) {
            cartEntity = new CartEntity();
            cartEntity.setUser(user);
            cartEntity = cartRepository.save(cartEntity);
        }
        BookEntity book = bookRepository.findOneByIdAndStatus(bookId, 0)
                .orElseThrow(() -> new RescourceNotFoundException());

        // tim cartItem co chua quyen sach muon them
        CartItemEntity cartItemEntity = cartItemRepository.findOneByBook_IdAndStatusAndCart_Id(bookId, 0, cartEntity.getId())
                .orElse(null);

        if(cartItemEntity == null) { // quyen sach nay chua co trong gio hang hoac da co nhung ma da dat hang
            cartItemEntity = CartItemConverter.toEntity(cartItem);
            cartItemEntity.setCart(cartEntity);
            cartItemEntity.setBook(book);
            cartItemEntity.setTotalPrice(cartItem.getQuantity() * (double)book.getPrice());
        }
        else { // da co trong gio hang va chua tien hanh dat hang
            // set lai quantity = oldQuantity + newQuantity
            Integer oldQuantity = cartItemEntity.getQuantity();
            cartItemEntity.setQuantity(oldQuantity + cartItem.getQuantity());
        }
        cartItemEntity = cartItemRepository.save(cartItemEntity);
        return CartItemConverter.toDto(cartItemEntity);
    }


    // delete cartItem
    @Override
    public String deleteCartItem(Long id) {
        // status = 0: trong gio hang
        // status = 1: da dc dat hang
//        CartItemEntity cartItemEntity = cartItemRepository.findByIdAndStatus(id, 0)
//                .orElseThrow(() -> new RescourceNotFoundException());
//        cartItemEntity.setStatus(1);
        if(cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
            return "Xóa sản phầm thành công";
        }
        return "Không tồn tại sản phẩm này trong giỏ hàng";

    }

    @Override
    public List<CartItemDto> getListCartItem(Integer status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity userEntity = userRepository.findByUserName(userName).orElse(null);
        CartEntity cartEntity = userEntity.getCart();
        if(cartEntity == null) {
            return new ArrayList<>();
        }
        List<CartItemEntity> entities = cartItemRepository.findAllByCart_IdAndStatus(cartEntity.getId(), status);
        List<CartItemDto> results = new ArrayList<>();
        for(CartItemEntity cartItemEntity : entities) {
            results.add(CartItemConverter.toDto(cartItemEntity));
        }
        return results;
    }

    @Override
    public Long countCartItem() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity userEntity = userRepository.findByUserName(userName).orElse(null);
        CartEntity cartEntity = userEntity.getCart();
        if(cartEntity == null) {
            return 0L;
        }
        Long count = cartItemRepository.countByCart_IdAAndStatus(cartEntity.getId(), 0);
        if(count == null) {
            count = 0L;
        }
        return count;
    }
}
