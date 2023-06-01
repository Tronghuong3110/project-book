package com.bookstore.converter;

import com.bookstore.model.dto.Recipient_addressDto;
import com.bookstore.model.entity.Recipient_addressEntity;

public class RecipientAddressConverter {

    public static Recipient_addressEntity toEntity(Recipient_addressDto recipientAddressDto) {
        Recipient_addressEntity recipientAddressEntity = new Recipient_addressEntity();
        recipientAddressEntity.setCommune(recipientAddressDto.getCommune());
        recipientAddressEntity.setConsious(recipientAddressDto.getConsious());
        recipientAddressEntity.setDistrict(recipientAddressDto.getDistrict());
        recipientAddressEntity.setNameReceiver(recipientAddressDto.getNameReceiver());
        recipientAddressEntity.setPhoneReceiver(recipientAddressDto.getPhoneReceiver());
        recipientAddressEntity.setNoteBill(recipientAddressDto.getNote());
        recipientAddressEntity.setVillage(recipientAddressDto.getVillage());
        return recipientAddressEntity;
    }
}
