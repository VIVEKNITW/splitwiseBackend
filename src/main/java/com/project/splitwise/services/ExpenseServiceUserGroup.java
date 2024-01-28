package com.project.splitwise.services;

import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.Group;
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

@Service
public class ExpenseServiceUserGroup {
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;
    private UserService userService;

    public ExpenseServiceUserGroup(UserRepository userRepository,
                                   ExpenseUserRepository expenseUserRepository,
                                   @Qualifier("bruteForceSettleUpStrategy")SettleUpStrategy settleUpStrategy,
                                   GroupRepository groupRepository,
                                   ExpenseRepository expenseRepository,
                                   UserService userService) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    public void settleUpUserInGroup(long groupId, long userId) throws UserDoesntExistException {
        Group group = groupRepository.findById(groupId).get();
        List<Expense> expenses = expenseRepository.findAllByGroup(group);
        for(Expense e:expenses){
            System.out.println(e.getDescription());

        }
        List<Transaction> transactions = settleUpStrategy.settle(expenses, userId);

        boolean needToSettle = false;
        for(Transaction t:transactions){
            if(t.getFrom().getId() == userId || t.getTo().getId() == userId){
                needToSettle = true;
                break;
            }
        }

        if(needToSettle){
            System.out.println("need to settle those expenses first");
        }
        else{
            System.out.println("can be removed");
            List<User> users = group.getMembers();
            List<User> updatedUsers = new ArrayList<>();
            for(User u:users){
                if(u.getId()!=userId){
                    updatedUsers.add(u);
                }
            }
            groupRepository.save(group);
        }

    }
}
