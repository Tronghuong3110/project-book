package com.bookstore.controller.user;

import java.util.List;

import com.bookstore.model.dto.ReviewDto;
import com.bookstore.model.response.ResponseReview;
import com.bookstore.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.dto.BookDto;
import com.bookstore.service.IBookService;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class BookApi {
	
	@Autowired
	private IBookService bookService;

	@Autowired
	private IReviewService reviewService;

	// get all book in database
	@GetMapping("/books")
	public List<BookDto> getAllBook() {
		List<BookDto> res = bookService.findAll();
		return res;
	}

	// get all book in database by category
	@GetMapping("/books/{categoryId}")
	public List<BookDto> findAllByCategory(@PathVariable("categoryId") String categoryId){
		List<BookDto> results = bookService.findnAllByCategory(Long.valueOf(categoryId));
		return results;
	}

	// get one book
	@GetMapping("/book/{id}")
	public BookDto findOneById(@PathVariable("id") String id) {
		BookDto book = bookService.findOne(Long.valueOf(id));
		return book;
	}

	// review
	@PostMapping("/review")
	public ReviewDto createReview(@RequestBody ReviewDto reviewDto, @RequestParam Long bookId) {
		return reviewService.save(reviewDto, bookId);
	}

	@GetMapping("/reviews")
	public ResponseReview getReviews(@RequestParam Long bookId) {
		return reviewService.findAll(bookId);
	}

	// update comment
	@PutMapping("/review")
	public ResponseEntity<?> update(@RequestBody ReviewDto reviewDto) {
		ResponseReview res = reviewService.update(reviewDto);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/review/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		String message = reviewService.delete(Long.valueOf(id));
		return ResponseEntity.ok(message);
	}
 }
