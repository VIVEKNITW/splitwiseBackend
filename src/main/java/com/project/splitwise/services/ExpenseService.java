package com.project.splitwise.services;

import com.project.splitwise.repositories.ExpenseRepository;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserExpenseRepository;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.strategies.settleupstrategy.SettleUpStrategy;
import com.project.splitwise.strategies.settleupstrategy.Transaction;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.models.expenseUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpenseService {
    private UserRepository userRepository;
    private UserExpenseRepository userExpenseRepository;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(UserRepository userRepository,
                          UserExpenseRepository userExpenseRepository,
                          @Qualifier("twoSetsSettleUpStrategy") SettleUpStrategy settleUpStrategy,
                          GroupRepository groupRepository,
                          ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }

    public List<Transaction> settleUpUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            // throw Exception
            return null;
        }

        List<expenseUser> userExpenses = userExpenseRepository.findAllByUser(userOptional.get());

        List<Expense> expensesInvolvingUser = new ArrayList<>();
        for (expenseUser userExpense: userExpenses) {
            expensesInvolvingUser.add(userExpense.getExpense());
        }

        List<Transaction> transactions = settleUpStrategy.settle(expensesInvolvingUser);

        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (transaction.getFrom().equals(userOptional.get()) || transaction.getTo().equals(userOptional.get())) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }

    public List<Transaction> settleUpGroup(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (groupOptional.isEmpty()) {
            // throw nexception
            return null;
        }

        List<Expense> expenses = expenseRepository.findAllByGroups(groupOptional.get());

        List<Transaction> transactions = settleUpStrategy.settle(
                expenses
        );

        return transactions;
    }
}

// BMS, Splitwise, TTT, PL,