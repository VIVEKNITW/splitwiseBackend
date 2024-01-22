package com.project.splitwise.repositories;

import com.project.splitwise.models.Expense;
import com.project.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense save(Expense expesne);

    List<Expense> findAllByGroup(Group group);
}
