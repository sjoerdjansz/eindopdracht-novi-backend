package nl.sweatdaddy.controller;

import jakarta.validation.Valid;
import java.util.List;
import nl.sweatdaddy.dto.ApiResponse;
import nl.sweatdaddy.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.dto.ExerciseResponseDto;
import nl.sweatdaddy.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<ApiResponse<List<ExerciseResponseDto>>> getAllExercises() {
    List<ExerciseResponseDto> exercises = exerciseService.getAll();

    if (exercises.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(
        new ApiResponse<>(
            exercises,
            "All exercises successfully retrieved"
        )
    );
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ExerciseResponseDto>> createExercise(
      @RequestBody @Valid CreateExerciseRequestDto request) {

    ExerciseResponseDto created = exerciseService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse<>(created, "Exercises added to library")
        );
  }

}


