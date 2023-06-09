package com.bookstore.service;

import com.bookstore.model.dto.Recipient_addressDto;
import com.bookstore.model.response.ResponseBill;

import java.util.*;

public interface IBillService {
    String saveBill(Recipient_addressDto recipientAddressDto, Float totalPay);
    List<ResponseBill> findAllByBillId();
    String deleteBill(Long billId);
    Boolean existsByIdAndStatus(Long id, Integer status);
}
