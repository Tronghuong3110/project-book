package com.bookstore.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.entity.CartItemEntity;
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

	// get all book in database
	@Override
	public List<BookDto> searchBookByNameOrAuthor(String key) {
//		List<BookEntity> listEntity = bookRepository.findAllByStatus(0);
		List<BookEntity> listEntity = bookRepository.searchBookByNameOrAuthor(key);
		List<BookDto> results = new ArrayList<>();
		for(BookEntity entity : listEntity) {
			results.add(BookConverter.toDto(entity));
		}
		return results;
	}

	@Override
	public BookDto findOne(Long id) {
		BookEntity entity = bookRepository.findOneByIdAndStatus(id, 0)
				.orElseThrow(() -> new RescourceNotFoundException());;
		
		return BookConverter.toDto(entity);
	}

	// add book or update book
	@Override
	public BookDto saveOrUpdate(BookDto book, Long categoryId) {
		BookEntity entity = new BookEntity();
		BookEntity oldBook = bookRepository.findOneByNameAndAuthorAndStatus(book.getName(), book.getAuthor(),0);
		
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
			if(oldBook == null || (oldBook != null && oldBook.getId().equals(book.getId()))) {
				entity = update(book);
				// Th thay đổi cả thể loại của quyển sách 
				if(!categoryId.equals(entity.getCategory().getId())) {
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
		entity.setStatus(0);
		entity.setSold_quantity(0);
		return entity;
	}
	
	// cập nhật quyển sách đã có
	private BookEntity update(BookDto book) {
		// lấy quyển sách trong database theo id
		BookEntity oldBook = bookRepository.findOneByIdAndStatus(book.getId(), 0)
				.orElseThrow(() -> new RescourceNotFoundException());
		BookEntity newBook = BookConverter.toEntity(oldBook, book);
		return newBook; 
	}

	// get all book by category
	@Override
	public List<BookDto> findnAllByCategory(Long categoryId) {
		List<BookEntity> entities = bookRepository.findAllByCategory_IdAndStatus(categoryId, 0);
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

	// delete book
	@Override
	public void delete(Long id) {
		// xoa tất cả comment và đánh giá của quyển sách đó
		List<ReviewEntity> reviews = reviewRepository.findAllByBook_Id(id);
		for(ReviewEntity review : reviews) {
			reviewRepository.deleteById(review.getId());
		}

		// xóa tất cả quyển sách có trong cartItem và trong giỏ hàng mà chưa được đặt
		List<CartItemEntity> cartItemEntities = cartItemRepository.findAllByBook_IdAndStatus(id, 0);
		for(CartItemEntity cartItemEntity : cartItemEntities) {
			cartItemRepository.deleteById(cartItemEntity.getId());
		}

		// update status of table book = 1 (đã xóa)
		BookEntity book = bookRepository.findById(id)
				.orElseThrow(() -> new RescourceNotFoundException());
		book.setStatus(1);
		bookRepository.save(book);
	}

}
