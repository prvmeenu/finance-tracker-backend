/**
 * package com.example.FinanceTracker;
 * <p>
 * <p>
 * import com.example.FinanceTracker.entity.Transaction;
 * import com.example.FinanceTracker.repository.TransactionRepository;
 * import net.datafaker.Faker;
 * import org.springframework.boot.CommandLineRunner;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * <p>
 * import java.time.LocalDate;
 * import java.time.ZoneId;
 * import java.util.ArrayList;
 * import java.util.Random;
 * import java.util.concurrent.TimeUnit;
 *
 * @Configuration public class DatabaseInitializer  {
 * @Bean CommandLineRunner loadData(TransactionRepository repo){
 * return args -> {
 * if (repo.count()>0){
 * return;
 * }
 * <p>
 * Faker faker = new Faker();
 * Random random = new Random();
 * <p>
 * ArrayList<String> expenseCategories = new ArrayList<String>();
 * expenseCategories.add("Food");
 * expenseCategories.add("Travel");
 * expenseCategories.add("Outing");
 * expenseCategories.add("Shopping");
 * <p>
 * ArrayList<String> incomeCategories = new ArrayList<String>();
 * incomeCategories.add("Salary");
 * incomeCategories.add("Bonus");
 * incomeCategories.add("Gift");
 * <p>
 * for(int i=0; i<50; i++){
 * Transaction t = new Transaction();
 * Transaction.Type type = random.nextBoolean() ? Transaction.Type.Income : Transaction.Type.Expense;
 * t.setType(type);
 * <p>
 * if(type == Transaction.Type.Income) {
 * String category = incomeCategories.get(random.nextInt(incomeCategories.size()));
 * t.setCategory(category);
 * } else if (type == Transaction.Type.Expense) {
 * String category = expenseCategories.get(random.nextInt(expenseCategories.size()));
 * t.setCategory(category);
 * <p>
 * }
 * <p>
 * double amount = faker.number().randomDouble(2,10,1000);
 * t.setAmount(amount);
 * <p>
 * java.util.Date fakeDate = faker.date().past(180, TimeUnit.DAYS);
 * LocalDate date = fakeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
 * t.setDate(date);
 * <p>
 * repo.save(t);
 * }
 * System.out.println("Fake Data Loaded!");
 * };
 * }
 * }
 **/