package com.example.FinanceTracker.repository;

import com.example.FinanceTracker.entity.Transaction;
import com.example.FinanceTracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCategoryIgnoreCase(String category);

    List<Transaction> findByUser(User user);

    Page<Transaction> findByUser(User user, Pageable pageable);

    List<Transaction> findByUserAndCategoryIgnoreCase(User user, String category);
}

