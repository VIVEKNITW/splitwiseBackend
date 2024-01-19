package com.project.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel {
    private double amount;
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Group group;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    List<ExpenseUser> expenseUsers;

}
