package com.project.splitwise.controllers;

import com.project.splitwise.dtos.SettleUpGroupResponseDto;
import com.project.splitwise.dtos.SettleUpUserResponseDto;
import com.project.splitwise.exceptions.GroupDoesntExistException;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.services.ExpenseServiceGroup;
import com.project.splitwise.services.ExpenseServiceUser;
import com.project.splitwise.strategies.settleupstrategy.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private ExpenseServiceGroup expenseServiceGroup;
    private ExpenseServiceUser expenseServiceUser;

    @Autowired
    public ExpenseController(ExpenseServiceGroup expenseService, ExpenseServiceUser expenseServiceUser) {
        this.expenseServiceGroup = expenseService;
        this.expenseServiceUser = expenseServiceUser;
    }

    @PostMapping("/settleUpUser/{userId}")
    public SettleUpUserResponseDto settleUpUser(@PathVariable(value = "userId") long userId) {
        System.out.println("Settling up for userid = "+userId);
        SettleUpUserResponseDto response = new SettleUpUserResponseDto();
        try{
            List<Transaction> transactions = expenseServiceUser
                    .settleUpUser(userId);
            response.setStatus("SUCCESS");
            List<String> T = new ArrayList<>();
            for(Transaction t:transactions){
                T.add("from: "+t.getFrom().getName()+ "; to: " + t.getTo().getName()+ "; amount= "+ t.getAmount());
            }
            response.setTransactions(T);
            response.setStatus("SUCCESS");

        }catch (UserDoesntExistException e){
            response.setMessage(e.getMessage());
            response.setStatus("FAILED");
        }


//        response.setTransactions(transactions);
        System.out.println("Returning from controller");
        return response;
//        return null;
    }

    @PostMapping("/settleUpGroup/{groupId}")
    public SettleUpGroupResponseDto settleUpGroup(@PathVariable long groupId) throws GroupDoesntExistException {
        System.out.println("Settling up for groupid = "+groupId);

        SettleUpGroupResponseDto responseDto = new SettleUpGroupResponseDto();
        try{
            List<Transaction> transactions = expenseServiceGroup.settleUpGroup(groupId);
            List<String> T = new ArrayList<>();
            for(Transaction t:transactions){
                T.add("from: "+t.getFrom().getName()+ "; to: " + t.getTo().getName()+ "; amount= "+ t.getAmount());
            }
            responseDto.setTransactions(T);
            responseDto.setMessage("Here's the set of transactions needed for complete settleup of your group");
        }
        catch (GroupDoesntExistException | UserDoesntExistException e){
            responseDto.setMessage(e.getMessage());
        }

        System.out.println("returning transaction list from controller");
        return responseDto;
    }
}
