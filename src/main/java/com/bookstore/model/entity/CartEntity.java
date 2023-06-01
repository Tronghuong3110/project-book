package com.bookstore.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@OneToMany(mappedBy = "cart")
	private List<CartItemEntity> cartItems = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<CartItemEntity> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemEntity> cartItems) {
		this.cartItems = cartItems;
	}

}
