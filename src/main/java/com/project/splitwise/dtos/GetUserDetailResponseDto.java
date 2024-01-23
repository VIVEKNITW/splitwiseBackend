package com.project.splitwise.dtos;

import lombok.Data;

@Data
public class GetUserDetailResponseDto {
    private String password;
    private String phoneNumber;
    private String userName;
    private String message;
}
