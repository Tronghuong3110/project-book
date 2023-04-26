package com.bookstore.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.Exception.RescourceNotFoundException;
import com.bookstore.converter.BookConverter;
import com.bookstore.model.dto.BookDto;
import com.bookstore.model.entity.BookEntity;
import com.bookstore.model.entity.CategoryEntity;
import com.bookstore.model.entity.ReviewEntity;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.repository.ReviewRepository;
import com.bookstore.service.IBookService;
import com.bookstore.util.UploadFileUtil;


@Service
public class BookService implements IBookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	public List<BookDto> findAll() {
		List<BookEntity> listEntity = bookRepository.findAll();
		List<BookDto> results = new ArrayList<>();
		for(BookEntity entity : listEntity) {
			results.add(BookConverter.toDto(entity));
		}
		return results;
	}

	@Override
	public BookDto findOne(Long id) {
		BookEntity entity = bookRepository.findById(id)
				.orElseThrow(() -> new RescourceNotFoundException());;
		
		return BookConverter.toDto(entity);
	}

	@Override
	public BookDto saveOrUpdate(BookDto book, Long categoryId) {
		BookEntity entity = new BookEntity();
		BookEntity oldBook = bookRepository.findOneByNameAndAuthor(book.getName(), book.getAuthor());
		
		// thêm mới sách
		if(book.getId() == null) {
			// đã tồn tại sách có tiêu đề và tác giả như vậy
			if(oldBook != null) {
				return null;
			}
			entity = save(book, categoryId);
		}
		
		// edit book in database
		else {
			// TH không tìm được sách nào trùng tiêu đề và tác giả
			// Hoặc có nhưng mà nó chính là quyển sách đang cần chỉnh sử
			if(oldBook == null || (oldBook != null && oldBook.getId() == book.getId())) {
				entity = update(book);
				// Th thay đổi cả thể loại của quyển sách 
				if(categoryId != entity.getCategory().getId()) {
					CategoryEntity category = categoryRepository.findById(categoryId)
							.orElseThrow(() -> new RescourceNotFoundException());
					entity.setCategory(category);
				}
			}
			else {
				return null;
			}
		}
		entity = bookRepository.save(entity);
		return BookConverter.toDto(entity);
	}
	
	// Lưu quyển sách mới
	private BookEntity save(BookDto book, Long CategoryId) {
		BookEntity entity = BookConverter.toEntity(book);
		CategoryEntity category = categoryRepository.findById(CategoryId)
				.orElseThrow(() -> new RescourceNotFoundException());
		entity.setCategory(category);
		
		return entity;
	}
	
	// cập nhật quyển sách đã có
	private BookEntity update(BookDto book) {
		// lấy quyển sách trong database theo id
		BookEntity oldBook = bookRepository.findById(book.getId())
				.orElseThrow(() -> new RescourceNotFoundException());
		BookEntity newBook = BookConverter.toEntity(oldBook, book);
		return newBook; 
	}

	@Override
	public List<BookDto> findnAllByCategory(Long categoryId) {
		List<BookEntity> entities = bookRepository.findAllByCategory_Id(categoryId);
		List<BookDto> listResult = new ArrayList<>();
		for(BookEntity entity : entities) {
			listResult.add(BookConverter.toDto(entity));
		}
		return listResult;
	}

	// lưu ảnh vào server
	@Override
	public String saveFile(MultipartFile file) throws IOException {
		String uploadDir = "uploads/book/";
		Path pathFile = UploadFileUtil.save(file, uploadDir);
		String pathStr = pathFile.toString().replace("\\", "/");
//		String pathStr = pathFile.toString();
		return pathStr;
	}

	@Override
	public void delete(Long id) {
		List<ReviewEntity> reviews = reviewRepository.findAllByBook_Id(id);
		for(ReviewEntity review : reviews) {
			reviewRepository.deleteById(review.getId());
		}
		// update status of table book = 1
		BookEntity book = bookRepository.findById(id)
				.orElseThrow(() -> new RescourceNotFoundException());
		
		book.setStatus(1);
		bookRepository.save(book);
	}

}
