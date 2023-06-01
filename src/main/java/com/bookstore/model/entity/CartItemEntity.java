package com.bookstore.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "status")
	private Integer status;

	@Column(name = "total_price", columnDefinition = "float")
	private Double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private BookEntity book;
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private CartEntity cart;

	@ManyToOne
	@JoinColumn(name = "bill_id")
	private BillEntity billEntity;
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public BookEntity getBook() {
		return book;
	}
	public void setBook(BookEntity book) {
		this.book = book;
	}
	public CartEntity getCart() {
		return cart;
	}
	public void setCart(CartEntity cart) {
		this.cart = cart;
	}


	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BillEntity getBillEntity() {
		return billEntity;
	}

	public void setBillEntity(BillEntity billEntity) {
		this.billEntity = billEntity;
	}
}
