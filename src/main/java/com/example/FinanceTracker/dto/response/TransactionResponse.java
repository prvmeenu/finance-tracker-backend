package com.example.FinanceTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private String type;
    private String category;
    private Double amount;
    private LocalDate date;
}
