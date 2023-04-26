package com.bookstore.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.dto.BookDto;
import com.bookstore.service.IBookService;

@RestController(value = "APIOfAdmin")
@CrossOrigin
public class BookApi {

	@Autowired
	private IBookService bookService;
	
	// get all book in đatabase
	@GetMapping("admin/book")
	public List<BookDto> findAll() {
		List<BookDto> results = bookService.findAll();
		return results;
	}
	
	// get one book by id
	@GetMapping("admin/book/{id}")
	public BookDto findOnd(@PathVariable("id") String id) {
		BookDto book = bookService.findOne(Long.valueOf(id));
		return book;
	}
	
	// add book 
	@PostMapping("/admin/book/{categoryId}")
	public BookDto saveBook(@RequestBody BookDto book, @PathVariable("categoryId") String categoryId) {
		BookDto response = bookService.saveOrUpdate(book, Long.valueOf(categoryId));
		return response;
	}

	// update book
	@PutMapping("/admin/book/{categoryId}")
	public BookDto editBook(@RequestBody BookDto book,
							@PathVariable("categoryId") String categoryId) {
		BookDto res = bookService.saveOrUpdate(book, Long.valueOf(categoryId));
		return res;
	}
	
	@DeleteMapping("/admin/book/{id}")
	public String deleteBook(@PathVariable("id") String id) {
		bookService.delete(Long.valueOf(id));
		return "success";
	}
}
