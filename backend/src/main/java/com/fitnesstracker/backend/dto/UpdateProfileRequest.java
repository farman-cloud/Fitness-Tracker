package com.fitnesstracker.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Positive(message = "Age must be positive")
    private Integer age;

    @PositiveOrZero(message = "Weight must be non-negative")
    private Double weight;

    @PositiveOrZero(message = "Height must be non-negative")
    private Double height;
}