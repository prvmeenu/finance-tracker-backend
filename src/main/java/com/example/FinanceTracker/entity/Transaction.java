package com.example.FinanceTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Transaction {

    //getter & Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public enum Type {
        Income,
        Expense;
    }

    ;
    private Type type;
    private String category;
    private Double amount;
    private LocalDate date;
    @ManyToOne
    @JsonIgnore
    private User user;

    public Transaction() {
    }

    //Constructor
    public Transaction(int id, Type type, String category, Double amount, LocalDate date, User user) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
        this.user = user;
    }

}
