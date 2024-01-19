package com.project.splitwise.repositories;

import com.project.splitwise.models.Expense;
import com.project.splitwise.models.User;
import com.project.splitwise.models.ExpenseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExpenseRepository extends JpaRepository<ExpenseUser, Long> {

    List<ExpenseUser> findAllByUser(User user);

    List<ExpenseUser> findAllByExpenseIn(List<Expense> expenses);
}
