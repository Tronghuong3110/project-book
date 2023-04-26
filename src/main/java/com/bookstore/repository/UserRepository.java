package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByUserName(String userName); // tim kiem user xem co ton tai trong DB khong
	Boolean existsByUserName(String userName); // ktra user name da tong tai trong DB chua
	Boolean existsByEmail(String email); // ktra email da ton tai trong DB chua
}
