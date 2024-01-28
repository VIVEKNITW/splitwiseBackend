package com.project.splitwise.services;

import com.project.splitwise.exceptions.GroupDoesntExistException;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Group;
import com.project.splitwise.repositories.ExpenseRepository;
import com.project.splitwise.repositories.ExpenseUserRepository;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserRepository;
import com.project.splitwise.strategies.settleupstrategy.SettleUpStrategy;
import com.project.splitwise.strategies.settleupstrategy.Transaction;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceGroup {
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceGroup(UserRepository userRepository,
                               ExpenseUserRepository expenseUserRepository,
                               @Qualifier("twoSetsSettleUpStrategy") SettleUpStrategy settleUpStrategy,
                               ExpenseRepository expenseRepository,
                               GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }


    public List<Transaction> settleUpGroup(Long groupId) throws GroupDoesntExistException, UserDoesntExistException {
        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (groupOptional.isEmpty()) {
             throw new GroupDoesntExistException("group with group id: "+groupId+ " not available");
//            return null;
        }

        List<Expense> expenses = expenseRepository.findAllByGroup(groupOptional.get());


        System.out.println("List of expenses for group are");
        int count = 1;
        for(Expense e:expenses){
            System.out.println("expense :"+count+" "+e.getDescription()+" "+e.getAmount());
        }

        List<Transaction> transactions = settleUpStrategy.settle(
                expenses, groupId
        );
        System.out.println("returning transaction lkist from service");
        return transactions;
    }
}

// BMS, Splitwise, TTT, PL,