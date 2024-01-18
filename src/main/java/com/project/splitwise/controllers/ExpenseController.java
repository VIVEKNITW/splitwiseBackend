//package com.project.splitwise.controllers;
//
//import com.project.splitwise.dtos.SettleUpGroupRequestDto;
//import com.project.splitwise.dtos.SettleUpGroupResponseDto;
//import com.project.splitwise.dtos.SettleUpUserRequestDto;
//import com.project.splitwise.dtos.SettleUpUserResponseDto;
//import com.project.splitwise.services.ExpenseService;
//import com.project.splitwise.strategies.settleupstrategy.Transaction;
//import org.springframework.stereotype.Controller;
//
//import java.util.List;
//
//@Controller
//public class ExpenseController {
//    private ExpenseService expenseService;
//
//
//    public ExpenseController(ExpenseService expenseService) {
//        this.expenseService = expenseService;
//    }
//
//    public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto request) {
//        List<Transaction> transactions = expenseService
//                .settleUpUser(request.getUserId());
//
//
//        SettleUpUserResponseDto response = new SettleUpUserResponseDto();
//        response.setStatus("SUCCESS");
//        response.setTransactions(transactions);
//
//        return response;
//    }
//
//    public SettleUpGroupResponseDto settleUpGroup(SettleUpGroupRequestDto request) {
//        return null;
//    }
//}
