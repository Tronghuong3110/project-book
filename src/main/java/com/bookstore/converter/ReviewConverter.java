package com.bookstore.converter;

import com.bookstore.model.dto.ReviewDto;
import com.bookstore.model.entity.ReviewEntity;

public class ReviewConverter {

	public static ReviewDto toDto(ReviewEntity entity) {
		ReviewDto dto = new ReviewDto();
		dto.setId(entity.getId());
		dto.setComment(entity.getComment());
		dto.setStar(entity.getStar());
		return dto;
	}
	
	public static ReviewEntity roEntity(ReviewDto dto) {
		ReviewEntity entity = new ReviewEntity();
		entity.setComment(dto.getComment());
		entity.setStar(dto.getStar());
		return entity;
	}
}
