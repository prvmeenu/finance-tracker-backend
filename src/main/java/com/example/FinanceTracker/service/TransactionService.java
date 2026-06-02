package com.example.FinanceTracker.service;

import com.example.FinanceTracker.dto.request.TransactionRequest;
import com.example.FinanceTracker.dto.response.BudgetStatusResponse;
import com.example.FinanceTracker.dto.response.CategoryExpenseResponse;
import com.example.FinanceTracker.dto.response.MonthlyReportResponse;
import com.example.FinanceTracker.dto.response.TransactionResponse;
import com.example.FinanceTracker.entity.Budget;
import com.example.FinanceTracker.entity.Transaction;
import com.example.FinanceTracker.entity.User;
import com.example.FinanceTracker.exception.ForbiddenException;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.repository.BudgetRepository;
import com.example.FinanceTracker.repository.TransactionRepository;
import com.example.FinanceTracker.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.datafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, BudgetRepository budgetRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
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

    public String generateFakeTransaction() {
        User user = getCurrentUser();
        Faker faker = new Faker();
        Random random = new Random();

        ArrayList<String> expenseCategories = new ArrayList<String>();
        expenseCategories.add("Food");
        expenseCategories.add("Travel");
        expenseCategories.add("Outing");
        expenseCategories.add("Shopping");

        ArrayList<String> incomeCategories = new ArrayList<String>();
        incomeCategories.add("Salary");
        incomeCategories.add("Bonus");
        incomeCategories.add("Gift");

        for (int i = 0; i < 50; i++) {
            Transaction t = new Transaction();
            Transaction.Type type = random.nextBoolean() ? Transaction.Type.Income : Transaction.Type.Expense;
            t.setType(type);

            if (type == Transaction.Type.Income) {
                String category = incomeCategories.get(random.nextInt(incomeCategories.size()));
                t.setCategory(category);
            } else if (type == Transaction.Type.Expense) {
                String category = expenseCategories.get(random.nextInt(expenseCategories.size()));
                t.setCategory(category);

            }

            double amount = faker.number().randomDouble(2, 10, 1000);
            t.setAmount(amount);

            java.util.Date fakeDate = faker.date().past(180, TimeUnit.DAYS);
            LocalDate date = fakeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            t.setDate(date);
            t.setUser(user);
            transactionRepository.save(t);
        }
        return "Fake Data Generated Successfully";
    }

    //dto changes
    private TransactionResponse mapToResponse(
            Transaction transaction) {

        return new TransactionResponse(

                transaction.getType().name(),
                transaction.getCategory(),
                transaction.getAmount(),
                transaction.getDate());
    }

    public Transaction saveTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();

        transaction.setType(Transaction.Type.valueOf(request.getType()));
        transaction.setCategory(request.getCategory());
        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        User user = getCurrentUser();
        transaction.setUser(user);

        return transactionRepository.save(transaction);

    }

    public Page<TransactionResponse> getTransaction(Pageable pageable) {
        User user = getCurrentUser();
        Page<Transaction> userTransaction = transactionRepository.findByUser(user, pageable);

        /**List<TransactionResponse> responses = new ArrayList<>();
         changing it because of the pagination
         * for(Transaction t: userTransaction){
         responses.add(mapToResponse(t));
         }**/
        return userTransaction.map(this::mapToResponse);
    }

    public Map<String, Double> calculateBalance() {
        User user = getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        double income = 0;
        double expense = 0;
        for (Transaction t : transactions) {
            if (t.getType() == Transaction.Type.Income) {
                income += t.getAmount();
            } else if (t.getType() == Transaction.Type.Expense) {
                expense += t.getAmount();

            }

        }
        Map<String, Double> result = new HashMap<>();
        result.put("income", income);
        result.put("expense", expense);
        result.put("balance", income - expense);

        return result;
    }

    public List<TransactionResponse> filterCategory(String category) {
        User user = getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUserAndCategoryIgnoreCase(user, category);
        //dto changes
        List<TransactionResponse> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                result.add(mapToResponse(t));
            }
        }
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No such Category found");
        }
        return result;
    }

    public List<TransactionResponse> filterMonth(int month, int year) {

        User user = getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        List<TransactionResponse> result = new ArrayList<>();
        for (Transaction t : transactions) {
            var date = t.getDate();
            if (date != null) {
                if (date.getYear() == year && date.getMonthValue() == month) {
                    result.add(mapToResponse(t));
                }
            }
        }
        return result;
    }

    public List<CategoryExpenseResponse> categoryReport() {
        User user = getCurrentUser();
        List<Transaction> transaction = transactionRepository.findByUser(user);
        Map<String, Double> result = new HashMap<>();
        List<CategoryExpenseResponse> responses = null;

        for (Transaction t : transaction) {
            String categories = t.getCategory();
            // double amount = result.getOrDefault(categories, 0.0); //t.getAmount();
            if (t.getType() == Transaction.Type.Expense) {
                double currentValue = result.getOrDefault(categories, 0.0);
                result.put(categories, currentValue + t.getAmount());
            }
            responses = new ArrayList<>();
            for (Map.Entry<String, Double> entry : result.entrySet()) {
                CategoryExpenseResponse dto = new CategoryExpenseResponse();
                dto.setCategory(entry.getKey());
                dto.setAmount(entry.getValue());
                responses.add(dto);
            }
        }
        return responses;
    }

    private List<Transaction> filterMonthEntities(
            int month,
            int year) {

        User currentUser = getCurrentUser();

        return transactionRepository
                .findByUser(currentUser)
                .stream()
                .filter(transaction ->
                        transaction.getDate()
                                .getMonthValue() == month
                                &&
                                transaction.getDate()
                                        .getYear() == year)
                .toList();
    }

    public MonthlyReportResponse monthlyReport(int month, int year) {
        List<Transaction> monthlyTransactions = filterMonthEntities(month, year);
        //Map<String, Object> result = new HashMap<>();//Changing to DTO
        double income = 0;
        double expense = 0;
        for (Transaction t : monthlyTransactions) {
            if (t.getType() == Transaction.Type.Income) {
                income += t.getAmount();
            } else if (t.getType() == Transaction.Type.Expense) {
                expense += t.getAmount();
            }
        }
        /** result.put("month", month);
         result.put("year", year);
         result.put("totalTransactions", monthlyTransactions.size());
         result.put("income", income);
         result.put("expense", expense);
         result.put("balance", income - expense);**/

        return new MonthlyReportResponse(month, year, monthlyTransactions.size(), income, expense, income - expense);
    }

    public List<CategoryExpenseResponse> categoryMonthlyReport(int month, int year) {
        List<Transaction> monthlyTransactions = filterMonthEntities(month, year);
        Map<String, Double> result = new HashMap<>();
        List<CategoryExpenseResponse> responses = null;

        for (Transaction t : monthlyTransactions) {
            String category = t.getCategory();
            if (t.getType() == Transaction.Type.Expense) {
                double amount = result.getOrDefault(category, 0.0);
                result.put(category, amount + t.getAmount());
            }
            //Dto CategoryExpenseResponse changes
            responses = new ArrayList<>();
            for (Map.Entry<String, Double> entry : result.entrySet()) {
                CategoryExpenseResponse dto = new CategoryExpenseResponse();
                dto.setCategory(entry.getKey());
                dto.setAmount(entry.getValue());
                responses.add(dto);
            }
        }
        return responses;
    }

    public BudgetStatusResponse budgetAnalysis(int month, int year, String category) {
        List<CategoryExpenseResponse> monthlyReport = categoryMonthlyReport(month, year);
        //CategoryExpenseResponse Dto changes
        double currentValue = 0.0;
        for (CategoryExpenseResponse report : monthlyReport) {
            if (report.getCategory().equalsIgnoreCase(category)) {
                currentValue = report.getAmount();
                break;
            }
        }

        //double currentValue = monthlyReport.getOrDefault(category, 0.0);
        Budget budget = budgetRepository.findByCategoryIgnoreCase(category);
        if (budget == null) {
            throw new ResourceNotFoundException("No budget limit for this Category " + category);
        }
        double budgetValue = budget.getBudgetLimit();
        double remaining = budgetValue - currentValue;
        if (currentValue > budgetValue) {
            return new BudgetStatusResponse(category, budgetValue, currentValue, remaining, "EXCEED");
        } else {
            return new BudgetStatusResponse(category, budgetValue, currentValue, remaining, "SAFE");
        }

    }

    public Transaction updateTransaction(int id, TransactionRequest request) {
        Optional<Transaction> t = transactionRepository.findById(id);
        if (t.isEmpty()) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        Transaction existingTransaction = t.get();
        User user = getCurrentUser();
        if (existingTransaction.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Unauthorized access");
        }
        existingTransaction.setType(Transaction.Type.valueOf(request.getType()));
        existingTransaction.setCategory(request.getCategory());
        existingTransaction.setAmount(request.getAmount());
        existingTransaction.setDate(request.getDate());

        return transactionRepository.save(existingTransaction);
    }

    public String deleteTransaction(int id) {
        Optional<Transaction> t = transactionRepository.findById(id);
        if (t.isEmpty()) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        Transaction existingTransaction = t.get();
        User user = getCurrentUser();
        if (existingTransaction.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Unauthorized access");
        }
        transactionRepository.delete(existingTransaction);

        return "Transaction deleted Successfully";
    }

    //Export transaction to CSV file.
    public void exportTransactionToCsv(HttpServletResponse response) throws IOException {
        User user = getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attahment; filename=transaction.csv");
        PrintWriter writer = response.getWriter();

        //CSV Header
        writer.println("Type,Category,Amount,Date");

        //CSV Data
        for (Transaction t : transactions) {
            writer.println(t.getType() + "," + t.getCategory() + "," + t.getAmount() + "," + t.getDate());
        }
        writer.flush();
        writer.close();
    }


}
