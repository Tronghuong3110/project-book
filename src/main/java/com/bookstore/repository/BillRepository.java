package com.bookstore.repository;

import com.bookstore.model.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {

    List<BillEntity> findAllByUser_Id(Long userId);
}
