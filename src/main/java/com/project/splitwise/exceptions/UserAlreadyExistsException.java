package com.project.splitwise.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(){
        super("User already exists. Try with new number");
    }

}
