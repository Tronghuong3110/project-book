package com.bookstore.converter;

import com.bookstore.model.dto.BookDto;
import com.bookstore.model.entity.BookEntity;

public class BookConverter {

	public static BookDto toDto(BookEntity entity) {
		BookDto dto = new BookDto();
		dto.setAuthor(entity.getAuthor());
		dto.setDecription(entity.getDecription());
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPublication_date(entity.getPublication_date());
		dto.setSold_quantity(entity.getSold_quantity());
		dto.setTitle(entity.getTitle());
		dto.setTotal_page(entity.getTotal_page());
		dto.setImage(entity.getImage());
		dto.setPrice(entity.getPrice());
		dto.setCategory(CategoryConverter.toDto(entity.getCategory()));
		return dto;
	}
	
	public static BookEntity toEntity(BookDto dto) {
		BookEntity entity = new BookEntity();
		entity.setAuthor(dto.getAuthor());
		entity.setDecription(dto.getDecription());
		entity.setName(dto.getName());
		entity.setPublication_date(dto.getPublication_date());
		entity.setSold_quantity(dto.getSold_quantity());
		entity.setTitle(dto.getTitle());
		entity.setImage(dto.getImage());
		entity.setPrice(dto.getPrice());
		entity.setTotal_page(dto.getTotal_page());
		return entity;
	}
	
	public static BookEntity toEntity(BookEntity book, BookDto dto) {
		if(dto.getAuthor() != null) {
			book.setAuthor(dto.getAuthor());
		}
		if(dto.getPublication_date() != null) {
			book.setPublication_date(dto.getPublication_date());
		}
		if(dto.getTitle() != null) {
			book.setTitle(dto.getTitle());
		}
		if(dto.getTotal_page() != null ) {
			book.setTotal_page(dto.getTotal_page());
		}
		if(dto.getDecription() != null) {
			book.setDecription(dto.getDecription());
		}
		if(!book.getImage().equals(dto.getImage()) && !dto.getImage().equals("")) {
			book.setImage(dto.getImage());
		}
		return book;
	}
	
}
