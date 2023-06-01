package com.bookstore.service;

import com.bookstore.model.dto.Recipient_addressDto;

public interface IBillService {
    String saveBill(Recipient_addressDto recipientAddressDto, Float totalPay);
}
