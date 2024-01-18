package com.project.splitwise.repositories;

import com.project.splitwise.models.Expense;
import com.project.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByGroups(Group group);
}
