package com.bookstore.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.dto.CategoryDto;
import com.bookstore.service.ICategoryService;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class CategoryApi {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/category")
	public List<CategoryDto> findAll() {
		List<CategoryDto> results = categoryService.findAll();
		return results;
	}
	
	@GetMapping("/category/{id}")
	public CategoryDto findOne(@PathVariable("id") String id) {
		CategoryDto category = categoryService.findOne(Long.valueOf(id));
		return category;
	}
	
	@PostMapping("/admin/category")
	@PreAuthorize("hasAuthority('ADMIN')")
	public CategoryDto save(@RequestBody CategoryDto category) {
		CategoryDto dto = categoryService.saveOrUpdate(category);
		return dto;
	}
	
	@PutMapping("/category")
	@PreAuthorize("hasAuthority('ADMIN')")
	public CategoryDto update(@RequestBody CategoryDto category) {
		CategoryDto dto = categoryService.saveOrUpdate(category);
		return dto;
	}
	
}
