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
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.service.IBookService;
import com.bookstore.util.UploadFileUtil;


@Service
public class BookService implements IBookService{

	private static String NAME_FILE;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
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
		BookEntity oldBook = bookRepository.findOneByTitleAndAuthor(book.getTitle(), book.getAuthor());
		
		// thêm mới sách
		if(book.getId() == null) {
			// đã tồn tại sách có tiêu đề và tác giả như vậy
			if(oldBook != null) {
				return null;
			}
			entity = save(book, categoryId);
		}
		
		else {
			
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
		BookEntity oleBook = bookRepository.findById(book.getId())
				.orElseThrow(() -> new RescourceNotFoundException());
		BookEntity newBook = BookConverter.toEntity(oleBook, book);
		return newBook; 
	}

	@Override
	public List<BookDto> findnAllByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveFile(MultipartFile file) throws IOException {
		String uploadDir = "uploads/book/";
		Path pathFile = UploadFileUtil.save(file, uploadDir);
		String pathStr = pathFile.toString().replace("\\", "/");
//		String pathStr = pathFile.toString();
		NAME_FILE = pathStr.substring(pathStr.lastIndexOf("/") + 1);
		System.out.print(NAME_FILE);
		return pathStr;
	}

}
