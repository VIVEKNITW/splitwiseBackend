package com.project.splitwise.exceptions;

public class PaidSumNotEqualToTotalAmount extends Exception{
    public PaidSumNotEqualToTotalAmount(String msg){
        super(msg);
    }
}
