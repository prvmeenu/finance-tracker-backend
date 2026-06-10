package com.example.FinanceTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse {
    private String category;
    private Double budgetLimit;
}
