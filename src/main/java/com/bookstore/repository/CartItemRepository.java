package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.CartItemEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long>{
	Optional<CartItemEntity> findOneByBook_IdAndStatusAndCart_Id(Long bookId, Integer status, Long cartId);
	Optional<CartItemEntity> findByIdAndStatus(Long id, Integer status);
	List<CartItemEntity> findAllByBook_IdAndStatus(Long bookId, Integer status);
	List<CartItemEntity> findAllByCart_IdAndStatus(Long cartId, Integer status);
	@Query(value = "select count(id) from cart_item where cart_id = :cartId and status = :status group by(cart_id)", nativeQuery = true)
	Long countByCart_IdAAndStatus(@Param("cartId") Long cartId, @Param("status") Integer status);
}
