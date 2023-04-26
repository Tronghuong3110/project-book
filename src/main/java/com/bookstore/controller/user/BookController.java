package com.bookstore.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.dto.BookDto;
import com.bookstore.service.IBookService;

@RestController
@CrossOrigin
public class BookController {
	
	@Autowired
	private IBookService bookService;

	@GetMapping("/user/books")
	public List<BookDto> getAllBook() {
		List<BookDto> res = bookService.findAll();
		return res;
	}
	
	@GetMapping("user/books/{categoryId}")
	public List<BookDto> findAllByCategory(@PathVariable("categoryId") String categoryId){
		List<BookDto> results = bookService.findnAllByCategory(Long.valueOf(categoryId));
		return results;
	}
	
	@GetMapping("user/book/{id}")
	public BookDto findOneById(@PathVariable("id") String id) {
		BookDto book = bookService.findOne(Long.valueOf(id));
		return book;
	}
 }
