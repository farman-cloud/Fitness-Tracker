package com.fitnesstracker.backend.service;

import com.fitnesstracker.backend.dto.ExerciseRequest;
import com.fitnesstracker.backend.dto.WorkoutRequest;
import com.fitnesstracker.backend.entity.Exercise;
import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.entity.Workout;
import com.fitnesstracker.backend.repository.ExerciseRepository;
import com.fitnesstracker.backend.repository.UserRepository;
import com.fitnesstracker.backend.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Add this

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Transactional // 1
    public Workout createWorkout(WorkoutRequest workoutRequest, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Workout workout = new Workout();
        workout.setName(workoutRequest.getName());
        workout.setDate(workoutRequest.getDate() != null ? workoutRequest.getDate() : java.time.LocalDate.now());
        workout.setUser(user);

        Workout savedWorkout = workoutRepository.save(workout);

        for (ExerciseRequest exerciseRequest : workoutRequest.getExercises()) {
            Exercise exercise = new Exercise();
            exercise.setName(exerciseRequest.getName());
            exercise.setSets(exerciseRequest.getSets());
            exercise.setReps(exerciseRequest.getReps());
            exercise.setWeight(exerciseRequest.getWeight());
            exercise.setWorkout(savedWorkout);

            exerciseRepository.save(exercise);
        }

        return savedWorkout;
    }
}