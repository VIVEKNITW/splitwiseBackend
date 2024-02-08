package com.project.splitwise.controllers;

import com.project.splitwise.dtos.*;
import com.project.splitwise.exceptions.UserAlreadyExistsException;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.User;
import com.project.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/updateUser/{userId}")
    public RegisterUserResponseDto updateUser(@RequestBody RegisterUserRequestDto request, @PathVariable long userId) {
        User user;
        RegisterUserResponseDto response = new RegisterUserResponseDto();

        try {
            user = userService.updateUser(
                    userId,
                    request.getUserName(),
                    request.getPhoneNumber(),
                    request.getPassword()
            );


            response.setUserId(user.getId());
            response.setStatus("SUCCESS");
            response.setMessage("User has been updated successfully");
        } catch (UserDoesntExistException e) {
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<GetUserDetailResponseDto> getUser(@PathVariable long id){
        GetUserDetailResponseDto response = new GetUserDetailResponseDto();
        try{
            User user = userService.findUser(id);
            response.setMessage("User with id= "+id+" found");
            response.setUserName(user.getName());
            response.setPhoneNumber(user.getPhone());
            response.setPassword("********");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UserDoesntExistException e){
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("deleteUser/{id}")
    public DeleteUserResponseDto deleteUser(@PathVariable long id){
        DeleteUserResponseDto responseDto = new DeleteUserResponseDto();
        try{
            userService.deleteUser(id);
            responseDto.setStatus("SUCCESS");
            responseDto.setMessage("user with id= "+id+" has been deleted successfully");
        }catch (UserDoesntExistException e){
            responseDto.setMessage("No user with id= "+id+"exists in database");
        }
        return responseDto;
    }




}
