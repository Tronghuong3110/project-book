package com.bookstore.service.impl;

import com.bookstore.Exception.RescourceNotFoundException;
import com.bookstore.converter.CartItemConverter;
import com.bookstore.converter.RecipientAddressConverter;
import com.bookstore.model.dto.CartItemDto;
import com.bookstore.model.dto.Recipient_addressDto;
import com.bookstore.model.entity.*;
import com.bookstore.model.response.ResponseBill;
import com.bookstore.repository.*;
import com.bookstore.service.IBillService;
import com.bookstore.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private BookRepository bookRepository;

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
        billEntity.setUser(userEntity);
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
            entity.setBill(billEntity);
            entity.setStatus(1);
            BookEntity bookEntity = entity.getBook();
            bookEntity.setSold_quantity(entity.getQuantity() + bookEntity.getSold_quantity());
            bookRepository.save(bookEntity);
            cartItemRepository.save(entity);
        }
    }

    @Override
    public List<ResponseBill> findAllByBillId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserEntity userEntity = userRepository.findByUserName(userName).orElse(null);
        if(userEntity == null) {
            return null;
        }

        List<ResponseBill> results = new ArrayList<>();
        List<BillEntity> listBill = billRepository.findAllByUser_Id(userEntity.getId());

        for(BillEntity bill : listBill) {
            ResponseBill responseBill = new ResponseBill();
            List<CartItemEntity> listCartItem = cartItemRepository.findAllByBill_IdAndStatus(bill.getId(), 1);
            List<CartItemDto> listCartItemDto = new ArrayList<>();
            for(CartItemEntity cartItem : listCartItem) {
                listCartItemDto.add(CartItemConverter.toDto(cartItem));
            }
            responseBill.setTotalPrice(bill.getTotalPrice());
            responseBill.setCreateDate(bill.getCreateDate());
            responseBill.setBillId(bill.getId());
            responseBill.setListCartitem(listCartItemDto);
            results.add(responseBill);
        }
        return results;
    }

    @Override
    public String deleteBill(Long billId) {
        BillEntity billEntity = billRepository.findById(billId)
                .orElseThrow(() -> new RescourceNotFoundException());

        Date createDateBill = billEntity.getCreateDate();
        Date currentDate = new Date(System.currentTimeMillis());
        Long time = currentDate.getTime() - createDateBill.getTime();
        Long day = TimeUnit.MILLISECONDS.toDays(time);
        if(day >= 1.5) {
            return "Đã quá thời gian hủy đặt hàng!";
        }
        System.out.print(day);
        Recipient_addressEntity recipientAddressEntity = recipientAddressRepository.findByBill_Id(billId).orElse(null);
        recipientAddressRepository.deleteById(recipientAddressEntity.getId());
        List<CartItemEntity> listCartItem = cartItemRepository.findAllByBill_IdAndStatus(billId, 1);
        for(CartItemEntity entity : listCartItem) {
            BookEntity bookEntity = entity.getBook();
            bookEntity.setSold_quantity(bookEntity.getSold_quantity() - entity.getQuantity());
            bookRepository.save(bookEntity);
            cartItemRepository.deleteById(entity.getId() );
        }
        billRepository.deleteById(billId);
        return "Hủy đặt hàng thành công";
    }

    @Override
    public Boolean existsByIdAndStatus(Long id, Integer status) {
        return bookRepository.existsByIdAndStatus(id, status);
    }

}
