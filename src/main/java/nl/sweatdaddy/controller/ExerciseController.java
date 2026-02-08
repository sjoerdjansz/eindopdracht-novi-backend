package nl.sweatdaddy.controller;

import java.util.List;
import nl.sweatdaddy.dto.ApiResponse;
import nl.sweatdaddy.dto.ExerciseDto;
import nl.sweatdaddy.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ExerciseDto>>> getAllExercises() {
    List<ExerciseDto> exercises = exerciseService.getAll();

    if(exercises.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(
        new ApiResponse<>(
            exercises,
            "All exercises succesfully retrieved"
        )
    );
  }

}


