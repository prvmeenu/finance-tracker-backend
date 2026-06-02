package com.example.FinanceTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportResponse {
    private int month;
    private int year;
    private int totalTransactions;
    private double income;
    private double expense;
    private double balance;

}
