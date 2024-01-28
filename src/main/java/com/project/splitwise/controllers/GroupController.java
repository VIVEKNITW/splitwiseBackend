package com.project.splitwise.controllers;

import com.project.splitwise.dtos.*;
import com.project.splitwise.exceptions.GroupDoesntExistException;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/addUsersToGroup/{groupId}")
    public AddUsersToGroupResponseDto addUsersToGroup(@RequestBody AddUsersToGroupRequestDto request, @PathVariable long groupId){
        AddUsersToGroupResponseDto responseDto = new AddUsersToGroupResponseDto();
        try{
            groupService.addUsersToGroup(groupId,  request.getIds());
            responseDto.setMessage("Added uses");
            responseDto.setStatus("SUCCESS");
        }catch (GroupDoesntExistException e){
            responseDto.setStatus("FAILURE");
        }
        return responseDto;
    }

    @PostMapping("/removeUserFromGroup/{groupId}")
    public RemoveUserFromGroupResponseDto removeUserFromGroup(@RequestBody RemoveUserFromGroupRequestDto request, @PathVariable long groupId){
        try{
            groupService.removeUserFromGroup(groupId, request.getUserId());
        }catch (UserDoesntExistException | GroupDoesntExistException e){
            System.out.println("catch block");
        }

        return null;
    }
}
