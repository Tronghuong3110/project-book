package com.bookstore.model.response;

import com.bookstore.model.dto.CartItemDto;

import java.util.*;

public class ResponseBill {
    private Long billId;
    private List<CartItemDto> listCartitem;
    private Float totalPrice;
    private Date createDate;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public List<CartItemDto> getListCartitem() {
        return listCartitem;
    }

    public void setListCartitem(List<CartItemDto> listCartitem) {
        this.listCartitem = listCartitem;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
