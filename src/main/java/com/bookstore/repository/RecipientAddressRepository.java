package com.bookstore.repository;

import com.bookstore.model.entity.Recipient_addressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientAddressRepository extends JpaRepository<Recipient_addressEntity, Long> {
    void deleteByBill_Id(Long billId);
    Optional<Recipient_addressEntity> findByBill_Id(Long billId);
}
