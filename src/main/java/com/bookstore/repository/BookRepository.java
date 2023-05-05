package com.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>{
	BookEntity findOneByNameAndAuthorAndStatus(String name, String author, Integer status);
	List<BookEntity> findAllByCategory_IdAndStatus(Long categoryId, Integer status);
	List<BookEntity> findAllByStatus(Integer status);
	Optional<BookEntity> findOneByIdAndStatus(Long id, Integer status);
}
