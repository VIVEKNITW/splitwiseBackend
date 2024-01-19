package com.project.splitwise.services;


import com.project.splitwise.exceptions.GroupDoesntExistException;
import com.project.splitwise.exceptions.PaidByListSizeException;
import com.project.splitwise.exceptions.PaidSumNotEqualToTotalAmount;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.*;
import com.project.splitwise.repositories.ExpenseRepository;
import com.project.splitwise.repositories.ExpenseUserRepository;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AddExpenseService {
    UserRepository userRepository;
    GroupRepository groupRepository;
    ExpenseRepository expenseRepository;

    ExpenseUserRepository expenseUserRepository;

    @Autowired
    public AddExpenseService(UserRepository userRepository, GroupRepository groupRepository,ExpenseRepository expenseRepository, ExpenseUserRepository expenseUserRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
        this.expenseUserRepository = expenseUserRepository;
    }

    
    public void saveExpense(double amount, List<Long> paidById, List<Double> paidAmount ,List<Long> dividedAmongId, Long addedById, Long groupId, String desc ) throws
            UserDoesntExistException,
            GroupDoesntExistException,
            PaidByListSizeException, PaidSumNotEqualToTotalAmount {

        List<User> paidBy = new ArrayList<>();

        for(Long id:paidById){
            Optional<User> optionalPaidBy = userRepository.findById(id);
            if(optionalPaidBy.isEmpty()) {
                throw new UserDoesntExistException("check the paid by users. user id: " + id + " doesn't exist");
            }
            paidBy.add(optionalPaidBy.get());

        }
        
        List<User> dividedAmong = new ArrayList<>();
        for(Long id:dividedAmongId){
            Optional<User> optionalDividedAmong = userRepository.findById(id);
            if(optionalDividedAmong.isEmpty()) {
                throw new UserDoesntExistException("check the divided among. user id: " + id + " doesn't exist");
            }
            dividedAmong.add(optionalDividedAmong.get());
        }

        Optional<User> optionalAddedBy= userRepository.findById(addedById);
        if(optionalAddedBy.isEmpty()){
            throw new UserDoesntExistException("check the added by id. user id: " + addedById + " doesn't exist");
        }

        if(paidAmount.size() != paidById.size()){
            throw new PaidByListSizeException();
        }

        Group group;
        if(groupId != null){
            Optional<Group> optionalGroup = groupRepository.findById(groupId);
            if(optionalGroup.isEmpty()){
                throw new GroupDoesntExistException("Group with id: "+groupId+" doesn't exist");
            }
            group = optionalGroup.get();
        }
        else{
            group = null;
        }
        
        double individualShare = amount/dividedAmong.size();
        HashMap<User, Double> hm = new HashMap<User, Double>();
        int count = 0;
        double sum = 0.0;
        for(User user:paidBy){
            if(hm.containsKey(user)){
                hm.put(user, hm.get(user)+paidAmount.get(count));
            }
            else{
                hm.put(user, paidAmount.get(count));
            }
            sum+=paidAmount.get(count);
            count++;

        }

        if(sum!=amount){
            throw new PaidSumNotEqualToTotalAmount("total sum should be = "+amount+" but it's summing up to : "+sum);
        }


        for(User user:dividedAmong){
            if(hm.containsKey(user)){
                hm.put(user, hm.get(user)-individualShare);
            }
            else{
                hm.put(user, -1*individualShare);
            }
        }

        Expense expense = new Expense();

        List<ExpenseUser> expenseUsers = new ArrayList<>();
        hm.forEach((user, amountValue) -> {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setExpense(expense);
            expenseUser.setUser(user);
            if(amountValue<0){
                expenseUser.setUserExpenseType(UserExpenseType.HAD_TO_PAY);
            }
            else{
                expenseUser.setUserExpenseType(UserExpenseType.PAID);
            }
            expenseUser.setAmount(amountValue);

            expenseUsers.add(expenseUser);
        });

        expense.setAmount(amount);
        expense.setDescription(desc);
        expense.setExpenseType(ExpenseType.REAL);
        expense.setCreatedBy(optionalAddedBy.get());
        expense.setGroup(group);
        expense.setExpenseUsers(expenseUsers);


        expenseRepository.save(expense);
    }
}

