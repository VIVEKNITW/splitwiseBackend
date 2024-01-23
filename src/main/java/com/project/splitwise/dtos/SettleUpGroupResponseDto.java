package com.project.splitwise.dtos;

import com.project.splitwise.strategies.settleupstrategy.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class SettleUpGroupResponseDto {
    String message;
    String status;
    List<String> transactions;
}
