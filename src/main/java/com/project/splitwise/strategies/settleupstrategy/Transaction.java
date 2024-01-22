package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Transaction {
    private User from;
    private User to;
    private Double amount;
}
