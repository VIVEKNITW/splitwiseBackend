package com.project.splitwise.repositories;

import com.project.splitwise.models.Expense;
import com.project.splitwise.models.User;
import com.project.splitwise.models.expenseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExpenseRepository extends JpaRepository<expenseUser, Long> {

    List<expenseUser> findAllByUser(User user);

    List<expenseUser> findAllByExpenseIn(List<Expense> expenses);
}
