package com.bookstore.repository;

import com.bookstore.model.entity.Recipient_addressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientAddressRepository extends JpaRepository<Recipient_addressEntity, Long> {
}
