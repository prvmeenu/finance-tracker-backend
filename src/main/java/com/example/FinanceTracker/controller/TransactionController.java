package com.example.FinanceTracker.controller;

import com.example.FinanceTracker.dto.request.TransactionRequest;
import com.example.FinanceTracker.dto.response.BudgetStatusResponse;
import com.example.FinanceTracker.dto.response.CategoryExpenseResponse;
import com.example.FinanceTracker.dto.response.MonthlyReportResponse;
import com.example.FinanceTracker.dto.response.TransactionResponse;
import com.example.FinanceTracker.entity.Transaction;
import com.example.FinanceTracker.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@Tag(name = "Transactions APIs")
public class TransactionController {


    private final TransactionService service;

    @Operation(summary = "Fake Data")
    @PostMapping("/fake")
    public String generateFakeTransaction() {
        return service.generateFakeTransaction();
    }

    @Operation(summary = "Add new Transaction")
    @PostMapping
    public Transaction addTransaction(@Valid @RequestBody TransactionRequest request) {

        return service.saveTransaction(request);
    }

    @Operation(summary = "Get all Transactions")
    @GetMapping
    public Page<TransactionResponse> getTransaction(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page,size);

        return service.getTransaction(pageable);
    }

    @Operation(summary = "Available Balance")
    @GetMapping("/balance")
    public Map<String, Double> availableBalance() {

        return service.calculateBalance();
    }

    @Operation(summary = "Category Transactions")
    @GetMapping("/category/{category}")
    public List<TransactionResponse> filterCategory(@PathVariable String category) {
        return service.filterCategory(category);
    }

    @Operation(summary = "Monthly Transactions")
    @GetMapping("/month")
    public List<TransactionResponse> filterMonth(@RequestParam int month, int year) {
        return service.filterMonth(month, year);
    }

    @Operation(summary = "Category report")
    @GetMapping("/report/category")
    public List<CategoryExpenseResponse> categoryReport() {
        return service.categoryReport();
    }

    @Operation(summary = "Monthly report")
    @GetMapping("/report/monthly")
    public MonthlyReportResponse monthlyReport(@RequestParam int month, int year) {
        return service.monthlyReport(month, year);
    }

    @Operation(summary = "Monthly expense report by category")
    @GetMapping("/report/monthly/category")
    public List<CategoryExpenseResponse> categoryMonthlyReport(@RequestParam int month, int year) {
        return service.categoryMonthlyReport(month, year);
    }

    @Operation(summary = "Analyze monthly expenses by category against budget limits")
    @GetMapping("/status")
    public BudgetStatusResponse budgetAnalysis(@RequestParam int month, int year, String category) {
        return service.budgetAnalysis(month, year, category);
    }

    @Operation(summary = "Update the Transaction")
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable int id, @Valid @RequestBody TransactionRequest request) {
        return service.updateTransaction(id, request);
    }

    @Operation(summary = "Delete Transaction")
    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable int id) {
        return service.deleteTransaction(id);
    }

    @Operation(summary = "Export to CSV file")
    @GetMapping("/export/csv")
    public void exportTransactionToCsv(HttpServletResponse response) throws IOException {
        service.exportTransactionToCsv(response);

    }
}
