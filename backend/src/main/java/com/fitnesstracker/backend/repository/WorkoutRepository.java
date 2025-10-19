package com.fitnesstracker.backend.repository;

import com.fitnesstracker.backend.entity.User;
import com.fitnesstracker.backend.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUser(User user);
}