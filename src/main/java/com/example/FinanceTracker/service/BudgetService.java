package com.example.FinanceTracker.service;

import com.example.FinanceTracker.dto.request.BudgetRequest;
import com.example.FinanceTracker.dto.response.BudgetStatusResponse;
import com.example.FinanceTracker.entity.Budget;
import com.example.FinanceTracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionService transactionService;

    public BudgetService(BudgetRepository budgetRepository, TransactionService transactionService) {
        this.budgetRepository = budgetRepository;
        this.transactionService = transactionService;
    }

    private BudgetStatusResponse mapToResponse(Budget budget) {
        return new BudgetStatusResponse(budget.getCategory(), budget.getBudgetLimit());
    }

    //Add new Budget
    public BudgetStatusResponse saveBudget(BudgetRequest budget) {

        Budget budgetAmount = new Budget();
        budgetAmount.setCategory(budgetAmount.getCategory());
        budgetAmount.setBudgetLimit(budgetAmount.getBudgetLimit());
        Budget saved = budgetRepository.save(budgetAmount);
        return mapToResponse(saved);
    }

    public List<BudgetStatusResponse> getBudget() {

        List<Budget> budgets = budgetRepository.findAll();
        List<BudgetStatusResponse> responses = new ArrayList<>();
        for (Budget b : budgets) {
            responses.add(mapToResponse(b));
        }
        return responses;
    }

    public Double getCategoryBudgetLimit(String category) {
        Budget budget = budgetRepository.findByCategoryIgnoreCase(category);
        return budget.getBudgetLimit();
    }
}
