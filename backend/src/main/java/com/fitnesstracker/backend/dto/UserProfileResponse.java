package com.fitnesstracker.backend.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private Double weight;
    private Double height;
    private Double bmi;
}