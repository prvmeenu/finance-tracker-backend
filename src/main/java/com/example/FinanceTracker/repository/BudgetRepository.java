package com.example.FinanceTracker.repository;

import com.example.FinanceTracker.entity.Budget;
import com.example.FinanceTracker.entity.Transaction;
import com.example.FinanceTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    Budget findByUserAndCategoryIgnoreCase(User user, String category);
    List<Budget> findByUser(User user);

}
