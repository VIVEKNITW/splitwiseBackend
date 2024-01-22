package com.project.splitwise.services;

import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.User;
import com.project.splitwise.repositories.ExpenseRepository;
import com.project.splitwise.repositories.ExpenseUserRepository;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.strategies.settleupstrategy.SettleUpStrategy;
import com.project.splitwise.strategies.settleupstrategy.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceUser {
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;

    public ExpenseServiceUser(UserRepository userRepository,
                              ExpenseUserRepository expenseUserRepository,
                              @Qualifier("bruteForceSettleUpStrategy") SettleUpStrategy settleUpStrategy,
                              GroupRepository groupRepository,
                              ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Transaction> settleUpUser(Long userId) throws UserDoesntExistException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserDoesntExistException("No user with id= " + userId);
        }
        System.out.println("Found user with id= " + userId);

        List<Long> expenseIds = expenseUserRepository.findDistinctExpenseIdsByUserId(userId);

        System.out.println("expenseIds"+ expenseIds);


        List<Expense> expenses = new ArrayList<>();
        for (long id : expenseIds) {
            System.out.print("PRINT STATEMENT:expense id" + id + " ");
            Optional<Expense> optionalExpense = expenseRepository.findById(id);
            if (!optionalExpense.isEmpty()) {
                expenses.add(optionalExpense.get());
            }
        }

        System.out.println("PRINT STATEMENT: size of expenses list= "+expenses.size());


        List<Transaction> transactions = settleUpStrategy.settle(expenses, userId);

        for (Transaction t : transactions) {
            System.out.println("from: " + t.getFrom().getName() + "; to: " + t.getTo().getName() + "; amount= " + t.getAmount());
        }

        System.out.println("Returning from controller");
//        return null;
        return transactions;
    }
}
