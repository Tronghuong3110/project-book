package com.bookstore.model.dto;

public class Recipient_addressDto {
    private Long id;
    private String nameReceiver;
    private String phoneReceiver;
    private String village; // thôn
    private String commune; // xã
    private String district; // Huyện
    private String consious;  // Tỉnh
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConsious() {
        return consious;
    }

    public void setConsious(String consious) {
        this.consious = consious;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
