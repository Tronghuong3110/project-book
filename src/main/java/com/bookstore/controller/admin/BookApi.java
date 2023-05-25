package com.bookstore.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.dto.BookDto;
import com.bookstore.service.IBookService;

@RestController(value = "APIOfAdmin")
@CrossOrigin
@RequestMapping("/api/admin")
public class BookApi {

	@Autowired
	private IBookService bookService;
	
	// get all book in đatabase
	@GetMapping("/book")
	public List<BookDto> getAllBook(@RequestParam String key) {
		List<BookDto> res = bookService.searchBookByNameOrAuthor(key);
		return res;
	}
	
	// get one book by id
	@GetMapping("/book/{id}")
	public BookDto findOnd(@PathVariable("id") String id) {
		BookDto book = bookService.findOne(Long.valueOf(id));
		return book;
	}
	
	// add book 
	@PostMapping("/book/{categoryId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public BookDto saveBook(@RequestBody BookDto book, @PathVariable("categoryId") String categoryId) {
		BookDto response = bookService.saveOrUpdate(book, Long.valueOf(categoryId));
		return response;
	}

	// update book
	@PutMapping("/book/{categoryId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public BookDto editBook(@RequestBody BookDto book,
							@PathVariable("categoryId") String categoryId) {
		BookDto res = bookService.saveOrUpdate(book, Long.valueOf(categoryId));
		return res;
	}

	// Delete Book
	@DeleteMapping("/book/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteBook(@PathVariable("id") String id) {
		bookService.delete(Long.valueOf(id));
		return "success";
	}
}
