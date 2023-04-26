package com.bookstore.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.dto.BookDto;

public interface IBookService {
	List<BookDto> findAll();
	List<BookDto> findnAllByCategory(Long categoryId);
	BookDto findOne(Long id);
	BookDto saveOrUpdate(BookDto book, Long categoryId);
	String saveFile(MultipartFile file) throws IOException;
	void delete(Long id);
}
