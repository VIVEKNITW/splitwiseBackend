package com.project.splitwise.controllers;

import com.project.splitwise.dtos.RegisterGroupRequestDto;
import com.project.splitwise.dtos.RegisterGroupResponseDto;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Group;
import com.project.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GroupController {
    GroupService groupService;
    @Autowired
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping("/addGroup")
    public RegisterGroupResponseDto addGroup(@RequestBody RegisterGroupRequestDto registerGroupRequestDto){
        RegisterGroupResponseDto response = new RegisterGroupResponseDto();
        try{
            response = groupService.registerGroup(registerGroupRequestDto.getName(),
                    registerGroupRequestDto.getCreatedById(),
                    registerGroupRequestDto.getMemberIds(),
                    registerGroupRequestDto.getDesc());


        }catch (UserDoesntExistException e){
            response.setMessage(e.getMessage());
        }
        return response;

    }
}
