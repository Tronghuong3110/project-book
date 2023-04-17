package com.bookstore.model.dto;

import java.sql.Date;
import java.util.List;

public class BookDto {

	private Long id;
	private String name;
	private String title;
	private String author;
	private Date publication_date;
	private Integer sold_quantity;
	private Integer total_page;
	private String image;
	private String decription;
	private CategoryDto category;
	private List<ReviewDto> reviews;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublication_date() {
		return publication_date;
	}
	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}
	public Integer getSold_quantity() {
		return sold_quantity;
	}
	public void setSold_quantity(Integer sold_quantity) {
		this.sold_quantity = sold_quantity;
	}
	public Integer getTotal_page() {
		return total_page;
	}
	public void setTotal_page(Integer total_page) {
		this.total_page = total_page;
	}
	public String getDecription() {
		return decription;
	}
	public void setDecription(String decription) {
		this.decription = decription;
	}
	public List<ReviewDto> getReviews() {
		return reviews;
	}
	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
}
