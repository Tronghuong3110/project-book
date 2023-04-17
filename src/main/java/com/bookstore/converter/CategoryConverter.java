package com.bookstore.converter;

import com.bookstore.model.dto.CategoryDto;
import com.bookstore.model.entity.CategoryEntity;

public class CategoryConverter {
	
	public static CategoryDto toDto(CategoryEntity entity) {
		CategoryDto dto = new CategoryDto();
		dto.setId(entity.getId());
		dto.setCode(entity.getCode());
		dto.setName(entity.getName());
		return dto;
	}
	
	public static CategoryEntity toEntity(CategoryDto dto) {
		CategoryEntity entity = new CategoryEntity();
		entity.setName(dto.getName());
		return entity;
	}
}
