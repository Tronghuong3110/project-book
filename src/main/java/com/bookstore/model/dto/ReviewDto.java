package com.bookstore.model.dto;

import java.sql.Date;

public class ReviewDto {
	
	private Long id;
	private String comment;
	private Integer star;
	private UserDto user;
	private Date createDate;
	private Date modifiedDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
