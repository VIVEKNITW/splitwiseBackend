package com.project.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExpenseUser extends BaseModel {
    @ManyToOne
    private User user;
    @ManyToOne
    private Expense expense;

    @Enumerated(EnumType.ORDINAL)
    private UserExpenseType userExpenseType;
    private double amount;
}
