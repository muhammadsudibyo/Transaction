package com.ecommerce.transaction.repo;

import com.ecommerce.transaction.model.Product;
import com.ecommerce.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    public List findAllByStatus(String status);
    public List findAllByUserName(String userId);
    public Transaction findById(int trxId);
}

