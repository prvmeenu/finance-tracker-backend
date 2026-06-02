package com.example.FinanceTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetStatusResponse {
    private String category;
    private double budgetLimit;
    private double spent;
    private double remaining;
    private String status;


    public BudgetStatusResponse(String category, double budgetLimit) {
    }
}
