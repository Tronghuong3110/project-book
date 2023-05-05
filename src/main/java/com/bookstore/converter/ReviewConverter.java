package com.bookstore.converter;

import com.bookstore.model.dto.ReviewDto;
import com.bookstore.model.dto.UserDto;
import com.bookstore.model.entity.ReviewEntity;
import com.bookstore.model.entity.UserEntity;

public class ReviewConverter {

	public static ReviewDto toDto(ReviewEntity entity) {
		ReviewDto dto = new ReviewDto();
		dto.setId(entity.getId());
		dto.setComment(entity.getComment());
		dto.setStar(entity.getStar());
		dto.setCreateDate(entity.getCreateDate());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setUser(toUserDto(entity.getUser()));
		return dto;
	}
	
	public static ReviewEntity toEntity(ReviewDto dto) {
		ReviewEntity entity = new ReviewEntity();
		entity.setComment(dto.getComment());
		entity.setStar(dto.getStar());
		return entity;
	}

	private static UserDto toUserDto(UserEntity userEntity) {
		return UserConverter.toDto(userEntity);
	}

}
