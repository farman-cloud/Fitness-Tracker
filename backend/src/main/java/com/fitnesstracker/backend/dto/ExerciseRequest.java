package com.fitnesstracker.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class ExerciseRequest {

    @NotBlank(message = "Exercise name is required.")
    private String name;

    @PositiveOrZero(message = "Sets must be 0 or more.")
    private int sets;

    @PositiveOrZero(message = "Reps must be 0 or more.")
    private int reps;

    @PositiveOrZero(message = "Weight must be 0 or more.")
    private double weight;
}