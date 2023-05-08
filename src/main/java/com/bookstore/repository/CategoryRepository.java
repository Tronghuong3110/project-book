package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.CategoryEntity;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
	boolean existsByNameAndCode(String name, String code);
	CategoryEntity findOneByNameAndCode(String name, String code);

	@Query(value = "select * from category where name like %:keyword% "
					+ "or code like %:keyword%", nativeQuery = true)
	List<CategoryEntity> searchByName(@Param("keyword") String keyword);
}
