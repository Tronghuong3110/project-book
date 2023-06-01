package com.bookstore.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userrr")
public class UserEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Column(name = "username", columnDefinition = "varchar(150)")
	private String userName;
	
	@Column(name = "fullname", columnDefinition = "nvarchar(150)")
	private String fullName;
	
	@Column(name = "password", columnDefinition = "text")
	private String passWord;
	
	@Column(name = "email", columnDefinition = "varchar(150)")
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	List<RoleEntity> roles = new ArrayList<>();

	@OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
	private List<BillEntity> billEntityList = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	private CartEntity cart;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<ReviewEntity> reviews = new ArrayList<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public List<ReviewEntity> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewEntity> reviews) {
		this.reviews = reviews;
	}

	public List<BillEntity> getBillEntityList() {
		return billEntityList;
	}

	public void setBillEntityList(List<BillEntity> billEntityList) {
		this.billEntityList = billEntityList;
	}
}
