package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private User from;
    private User to;
    private Double amount;
}
