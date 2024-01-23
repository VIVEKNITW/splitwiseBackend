package com.project.splitwise.dtos;


import lombok.Data;

@Data
public class UpdateUserResponseDto {
    private Long userId;
    private String status;
    private String message;
}
