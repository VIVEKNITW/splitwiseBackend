package com.project.splitwise.exceptions;

public class UserDoesntExistException extends Throwable {
    public UserDoesntExistException(String msg){
        super(msg);
    }
}
