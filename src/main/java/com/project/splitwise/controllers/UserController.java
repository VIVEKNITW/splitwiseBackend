package com.project.splitwise.controllers;

import com.project.splitwise.dtos.RegisterUserRequestDto;
import com.project.splitwise.dtos.RegisterUserResponseDto;
import com.project.splitwise.exceptions.UserAlreadyExistsException;
import com.project.splitwise.models.User;
import com.project.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public RegisterUserResponseDto registerUser(@RequestBody RegisterUserRequestDto request) {
        User user;
        RegisterUserResponseDto response = new RegisterUserResponseDto();

        try {
            user = userService.registerUser(
                    request.getUserName(),
                    request.getPhoneNumber(),
                    request.getPassword()
            );


            response.setUserId(user.getId());
            response.setStatus("SUCCESS");
            response.setMessage("User has been added successfully");
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            response.setStatus("FAILURE");
            response.setMessage(userAlreadyExistsException.getMessage());
        }

        return response;
    }

    @PostMapping("/test")
    public RegisterUserResponseDto test(@RequestBody RegisterUserRequestDto request){
        System.out.println("You're into test method");
        RegisterUserResponseDto response = new RegisterUserResponseDto();
        response.setMessage("here's the dummy message");
        response.setUserId(1234L);
        response.setStatus("OK");
        return response;
    }
}
