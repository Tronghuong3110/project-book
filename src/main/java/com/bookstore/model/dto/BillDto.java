package com.bookstore.model.dto;

import com.bookstore.model.response.ResponseBill;

public class BillDto {

    private ResponseBill responseBill;
    private Recipient_addressDto recipientAddressDto;

    public ResponseBill getResponseBill() {
        return responseBill;
    }

    public void setResponseBill(ResponseBill responseBill) {
        this.responseBill = responseBill;
    }

    public Recipient_addressDto getRecipientAddressDto() {
        return recipientAddressDto;
    }

    public void setRecipientAddressDto(Recipient_addressDto recipientAddressDto) {
        this.recipientAddressDto = recipientAddressDto;
    }
}
