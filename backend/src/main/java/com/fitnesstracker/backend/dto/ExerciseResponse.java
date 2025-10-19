package com.fitnesstracker.backend.dto;

import lombok.Data;

@Data
public class ExerciseResponse {
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private double weight;
}