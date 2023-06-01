package com.bookstore.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "review")
public class ReviewEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Column(name = "comment", columnDefinition = "ntext")
	private String comment;
	
	@Column(name = "star")
	private Integer star;

	@Column(name = "create_date", columnDefinition = "date")
	private Date createDate;

	@Column(name = "modified_date", columnDefinition = "date")
	private Date modifiedDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private BookEntity book;

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

	public Long getId() {
		return id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
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
