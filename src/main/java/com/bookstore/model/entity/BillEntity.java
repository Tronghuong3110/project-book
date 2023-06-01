package com.bookstore.model.entity;

import java.sql.Date;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "bill")
public class BillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Column(name = "carete_date")
	private Date createDate;
	
	@Column(name = "code", length = 26)
	private String code;

	@Column(name = "total_price")
	private Float totalPrice;
	
	@OneToOne(mappedBy = "bill")
	private Recipient_addressEntity billDetail;

	@OneToMany(mappedBy = "billEntity")
	private List<CartItemEntity> listCartItemEntity = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	public BillEntity() {
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public List<CartItemEntity> getListCartItemEntity() {
		return listCartItemEntity;
	}

	public void setListCartItemEntity(List<CartItemEntity> listCartItemEntity) {
		this.listCartItemEntity = listCartItemEntity;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
}
