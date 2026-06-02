package com.example.FinanceTracker.repository;

import com.example.FinanceTracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    Budget findByCategoryIgnoreCase(String category);

}
