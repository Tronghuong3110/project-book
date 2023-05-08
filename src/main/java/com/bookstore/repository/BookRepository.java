package com.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>{
	BookEntity findOneByNameAndAuthorAndStatus(String name, String author, Integer status);
	List<BookEntity> findAllByCategory_IdAndStatus(Long categoryId, Integer status);
//	List<BookEntity> findAllByStatus(Integer status);
	Optional<BookEntity> findOneByIdAndStatus(Long id, Integer status);

	// search book by name or author
//	@Query(value = "select * from book where name like %:keyword% or author like %:keyword% and status = 0", nativeQuery = true)
	@Query(value = "select * from book where (name like %:keyword% "
					+ "or author like %:keyword%) "
					+ "and status = 0",
					   nativeQuery = true)
	List<BookEntity> searchBookByNameOrAuthor(@Param("keyword") String keyword);
}
