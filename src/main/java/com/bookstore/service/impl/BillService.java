package com.bookstore.service.impl;

import com.bookstore.converter.RecipientAddressConverter;
import com.bookstore.model.dto.Myuser;
import com.bookstore.model.dto.Recipient_addressDto;
import com.bookstore.model.entity.*;
import com.bookstore.repository.BillRepository;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.RecipientAddressRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.IBillService;
import com.bookstore.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;

import java.sql.Date;

@Service
public class BillService implements IBillService {

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RecipientAddressRepository recipientAddressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveBill(Recipient_addressDto recipientAddressDto, Float totalPay) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity userEntity = userRepository.findByUserName(userName).orElse(null);

        if(!Constants.validatePhoneNumber(recipientAddressDto.getPhoneReceiver())) {
            return "";
        }

        // Create bill
        BillEntity billEntity = new BillEntity();
        billEntity.setCreateDate(new Date(System.currentTimeMillis()));
        billEntity.setTotalPrice(totalPay);
        billEntity.setUserEntity(userEntity);
        billEntity = billRepository.save(billEntity);

        // Create Recipient_address
        Recipient_addressEntity recipientAddressEntity = RecipientAddressConverter.toEntity(recipientAddressDto);
        recipientAddressEntity.setBill(billEntity);
        recipientAddressRepository.save(recipientAddressEntity);
        updateCartItem(billEntity, userEntity);
        return "Thanh toán thành công";
    }

    private void updateCartItem(BillEntity billEntity, UserEntity userEntity) {

        CartEntity cartEntity = userEntity.getCart();
        if(cartEntity == null) {
            return;
        }
        List<CartItemEntity> listCartItem = cartItemRepository.findAllByCart_IdAndStatus(cartEntity.getId(), 0);
        for(CartItemEntity entity : listCartItem) {
            entity.setBillEntity(billEntity);
            entity.setStatus(1);
            cartItemRepository.save(entity);
        }
    }
}
