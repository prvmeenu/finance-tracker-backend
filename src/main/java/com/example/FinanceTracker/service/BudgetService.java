package com.example.FinanceTracker.service;

import com.example.FinanceTracker.dto.request.BudgetRequest;
import com.example.FinanceTracker.dto.response.BudgetResponse;
import com.example.FinanceTracker.dto.response.BudgetStatusResponse;
import com.example.FinanceTracker.entity.Budget;
import com.example.FinanceTracker.entity.User;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.repository.BudgetRepository;
import com.example.FinanceTracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        User user = optionalUser.get();

        return user;
    }

    private BudgetStatusResponse mapToResponse(Budget budget) {
        return new BudgetStatusResponse(budget.getCategory(), budget.getBudgetLimit());
    }

    //Add new Budget
    public BudgetResponse saveBudget(BudgetRequest budget) {

        User user = this.getCurrentUser();
        Budget budgetAmount = new Budget();
        budgetAmount.setCategory(budget.getCategory());
        budgetAmount.setBudgetLimit(budget.getLimitAmount());
        budgetAmount.setUser(user);
        Budget saved = budgetRepository.save(budgetAmount);
        return new BudgetResponse(saved.getCategory(),saved.getBudgetLimit());
    }

    public List<BudgetResponse> getBudget() {
        User user = this.getCurrentUser();
        List<Budget> budgets = budgetRepository.findByUser(user);
        List<BudgetResponse> responses = new ArrayList<>();
        for (Budget b : budgets) {
            BudgetResponse dto = new BudgetResponse(b.getCategory(),b.getBudgetLimit());
            responses.add(dto);
        }
        return responses;
    }

    public Double getCategoryBudgetLimit(String category) {
        User user = this.getCurrentUser();
        Budget budget = budgetRepository.findByUserAndCategoryIgnoreCase(user,category);
        return budget.getBudgetLimit();
    }
}
