package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>{
	BookEntity findOneByNameAndAuthor(String name, String author);
	List<BookEntity> findAllByCategory_Id(Long categoryId);
}
