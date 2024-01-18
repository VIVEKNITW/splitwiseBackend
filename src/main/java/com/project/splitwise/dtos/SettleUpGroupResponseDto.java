package com.project.splitwise.dtos;

import com.project.splitwise.strategies.settleupstrategy.Transaction;

import java.util.List;

public class SettleUpGroupResponseDto {
    String message;
    String status;
    List<Transaction> transactions;
}
