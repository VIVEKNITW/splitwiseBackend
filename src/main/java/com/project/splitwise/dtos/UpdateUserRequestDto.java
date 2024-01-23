package com.project.splitwise.dtos;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String password;
    private String phoneNumber;
    private String userName;

}
