package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterGroupRequestDto {
    private String name;
    private Long createdById;
    private List<Long> memberIds;
    private String desc;
}


/*
String name, User createdBy, List<Long> memberIds, String desc)
 */