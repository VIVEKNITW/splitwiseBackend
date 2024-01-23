package com.project.splitwise.exceptions;

public class PaidByListSizeException extends Exception{
    public PaidByListSizeException(){
        super("paidBy list size and paidAmount list size are different. They should be same");
    }

}
