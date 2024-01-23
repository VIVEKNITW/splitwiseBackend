package com.project.splitwise.controllers;

import com.project.splitwise.dtos.AddExpenseRequestDto;
import com.project.splitwise.dtos.AddExpensesResponseDto;
import com.project.splitwise.exceptions.GroupDoesntExistException;
import com.project.splitwise.exceptions.PaidByListSizeException;
import com.project.splitwise.exceptions.PaidSumNotEqualToTotalAmount;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.services.AddExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddExpenseController {
    AddExpenseService addExpenseService;

    @Autowired
    public AddExpenseController(AddExpenseService addExpenseService){
        this.addExpenseService = addExpenseService;
    }

    @PostMapping("/addExpense")
    public AddExpensesResponseDto addExpense(@RequestBody AddExpenseRequestDto requestDto){
        System.out.println(requestDto.getTotalAmount());
        System.out.println(requestDto.getPaidBy());
        System.out.println(requestDto.getPaidByAmount());
        System.out.println(requestDto.getDividedAmong());
        System.out.println(requestDto.getAddedBy());
        System.out.println(requestDto.getGroupId());


        AddExpensesResponseDto responseDto = new AddExpensesResponseDto();
        try{
            addExpenseService.saveExpense(requestDto.getTotalAmount(), requestDto.getPaidBy(), requestDto.getPaidByAmount(),
                    requestDto.getDividedAmong(), requestDto.getAddedBy(), requestDto.getGroupId(), requestDto.getDescription());
            responseDto.setMessage("successful");
        }catch (UserDoesntExistException | GroupDoesntExistException | PaidByListSizeException |
                PaidSumNotEqualToTotalAmount e){
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

}
