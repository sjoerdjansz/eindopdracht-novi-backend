package nl.sweatdaddy.service;

import java.util.List;
import nl.sweatdaddy.dto.ExerciseDto;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
  public List<ExerciseDto> getAll() {
    return List.of(
        new ExerciseDto(
            "Back Squat",
            "Quadriceps, Glutes, Hamstrings",
            "Squat"
        ),
        new ExerciseDto(
            "Front Squat",
            "Quadriceps, Core",
            "Squat"
        ),
        new ExerciseDto(
            "Bench Press",
            "Chest, Triceps, Shoulders",
            "Horizontal Push"
        ),
        new ExerciseDto(
            "Deadlift",
            "Hamstrings, Glutes, Lower Back",
            "Hip Hinge"
        ),
        new ExerciseDto(
            "Overhead Press",
            "Shoulders, Triceps, Upper Chest",
            "Vertical Push"
        ),
        new ExerciseDto(
            "Pull-Up",
            "Lats, Upper Back, Biceps",
            "Vertical Pull"
        ),
        new ExerciseDto(
            "Barbell Row",
            "Upper Back, Lats, Biceps",
            "Horizontal Pull"
        ),
        new ExerciseDto(
            "Romanian Deadlift",
            "Hamstrings, Glutes",
            "Hip Hinge"
        ),
        new ExerciseDto(
            "Lunge",
            "Quadriceps, Glutes",
            "Single-Leg"
        ),
        new ExerciseDto(
            "Push-Up",
            "Chest, Triceps, Core",
            "Horizontal Push"
        )
    );
  }
}
