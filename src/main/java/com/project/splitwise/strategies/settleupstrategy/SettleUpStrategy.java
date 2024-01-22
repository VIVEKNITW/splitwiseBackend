package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {

    List<Transaction> settle(List<Expense> expenses, long id) throws UserDoesntExistException;
}
