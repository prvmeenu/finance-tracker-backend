package com.example.FinanceTracker.controller;

import com.example.FinanceTracker.dto.request.BudgetRequest;
import com.example.FinanceTracker.dto.response.BudgetStatusResponse;
import com.example.FinanceTracker.service.BudgetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgets")
@Tag(name = "Budget APIs")
public class BudgetController {

    private final BudgetService service;

    @PostMapping
    public BudgetStatusResponse addBudgetLimits(@Valid @RequestBody BudgetRequest budget) {

        return service.saveBudget(budget);
    }

    @GetMapping
    public List<BudgetStatusResponse> getAllBudgetLimits() {
        return service.getBudget();
    }

    @GetMapping("/category")
    public Double getCategoryBudgetLimit(String category) {
        return service.getCategoryBudgetLimit(category);
    }
}
