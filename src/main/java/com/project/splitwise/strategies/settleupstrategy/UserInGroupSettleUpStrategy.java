package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Expense;

import java.util.List;

public class UserInGroupSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settle(List<Expense> expenses, long id) throws UserDoesntExistException {
        return null;
    }
}
