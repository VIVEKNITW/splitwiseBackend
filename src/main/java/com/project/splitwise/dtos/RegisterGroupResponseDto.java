package com.project.splitwise.dtos;


import com.project.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterGroupResponseDto {
    private String name;
    private List<String> addedUsers;
    private List<Long> missingIds;
    private Long groupId;
    private String message;
}

/*
    private String description;

    @ManyToMany
    private List<User> members;

    @ManyToOne
    private User createdBy;
    private String name;
 */
