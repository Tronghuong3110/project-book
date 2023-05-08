package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.Exception.RescourceNotFoundException;
import com.bookstore.converter.CategoryConverter;
import com.bookstore.model.dto.CategoryDto;
import com.bookstore.model.entity.CategoryEntity;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.service.ICategoryService;
import com.bookstore.util.Constants;

@Service
public class CategoryService implements ICategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<CategoryDto> findAll(String key) {
//		List<CategoryEntity> listEntity = categoryRepository.findAll();
		List<CategoryEntity> listEntity = categoryRepository.searchByName(key);
		List<CategoryDto> results = new ArrayList<>();
		for(CategoryEntity entity : listEntity) {
			results.add(CategoryConverter.toDto(entity));
		}
		return results;
	}

	@Override
	public CategoryDto saveOrUpdate(CategoryDto category) {
		CategoryEntity entity = new CategoryEntity();
		CategoryEntity oldCategory = categoryRepository.findOneByNameAndCode(category.getName(), Constants.standardized(category.getCode()));
		
		if(category.getId() == null) {
			entity = save(category);
			// check code cos trung khong
			if(oldCategory != null) {
				return null;
			}
		}
		else {
			if(oldCategory == null || (oldCategory != null && oldCategory.getId().equals(category.getId()))) {
				entity = update(category);
			}
			else {
				return null;
			}
		}
		//if(!categoryRepository.existsByNameAndCode(entity.getName(), entity.getCode())) {
			categoryRepository.save(entity);
			return CategoryConverter.toDto(entity);
		//}
//		return null;
	}
	
	private CategoryEntity save(CategoryDto dto) {
		System.out.print(dto.getCode());
		String code = Constants.standardized(dto.getCode());
		CategoryEntity entity = CategoryConverter.toEntity(dto);
		System.out.print(code);
		entity.setCode(code);
		return entity;
	}
	
	private CategoryEntity update(CategoryDto dto) {
		CategoryEntity entity = categoryRepository.findById(dto.getId())
				.orElseThrow(() -> new RescourceNotFoundException());
		if(dto.getName() != null) {
			entity.setName(dto.getName());
		}
		if(dto.getCode() != null) {
			String code = Constants.standardized(dto.getCode());
			entity.setCode(code);
		}
		return entity;
	}

	@Override
	public CategoryDto findOne(Long id) {
		CategoryEntity category = categoryRepository.findById(id)
				.orElseThrow(() -> new RescourceNotFoundException());
		return CategoryConverter.toDto(category);
	}

}
