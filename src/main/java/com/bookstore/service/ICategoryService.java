package com.bookstore.service;

import java.util.List;

import com.bookstore.model.dto.CategoryDto;

public interface ICategoryService {
	List<CategoryDto> findAll(String key);
	CategoryDto findOne(Long id);
	CategoryDto saveOrUpdate(CategoryDto category);
}
