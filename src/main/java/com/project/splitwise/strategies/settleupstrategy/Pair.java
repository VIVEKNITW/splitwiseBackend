package com.project.splitwise.strategies.settleupstrategy;

import com.project.splitwise.models.User;

public class Pair {
    User user;
    Double val;

    public Pair(User user, Double val) {
        this.user = user;
        this.val = val;
    }
}
