package com.project.splitwise.repositories;

import com.project.splitwise.models.ExpenseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
    ExpenseUser save(ExpenseUser expenseUser);
}
