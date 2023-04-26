package com.bookstore.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.dto.CategoryDto;
import com.bookstore.service.ICategoryService;

@RestController
@CrossOrigin
public class CategoryApi {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/admin/category")
	public List<CategoryDto> findAll() {
		List<CategoryDto> results = categoryService.findAll();
		return results;
	}
	
	@GetMapping("/admin/category/{id}")
	public CategoryDto findOne(@PathVariable("id") String id) {
		CategoryDto category = categoryService.findOne(Long.valueOf(id));
		return category;
	}
	
	@PostMapping("/admin/category")
	public CategoryDto save(@RequestBody CategoryDto category) {
		CategoryDto dto = categoryService.saveOrUpdate(category);
		return dto;
	}
	
	@PutMapping("/admin/category")
	public CategoryDto update(@RequestBody CategoryDto category) {
		CategoryDto dto = categoryService.saveOrUpdate(category);
		return dto;
	}
	
}
