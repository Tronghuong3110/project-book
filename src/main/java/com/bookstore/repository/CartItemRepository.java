package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.CartItemEntity;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long>{
	Optional<CartItemEntity> findOneByBook_IdAndStatusAndCart_Id(Long bookId, Integer status, Long cartId);
	Optional<CartItemEntity> findByIdAndStatus(Long id, Integer status);
}
