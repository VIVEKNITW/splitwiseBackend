package com.project.splitwise.repositories;

import com.project.splitwise.models.Expense;
import com.project.splitwise.models.ExpenseUser;
import com.project.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
    ExpenseUser save(ExpenseUser expenseUser);
    List<ExpenseUser> findAllByUser(User user);
    List<ExpenseUser> findAllByExpenseIn(List<Expense> expenses);

    @Query("SELECT DISTINCT eu.expense.id FROM ExpenseUser eu WHERE eu.user.id = :userId")
    List<Long> findDistinctExpenseIdsByUserId(Long userId);

    @Query("SELECT DISTINCT eu.user.id FROM ExpenseUser eu WHERE eu.expense.id = :expenseId")
    List<Long> findDistinctUserIdsByExpenseId(Long expenseId);

}
