package nl.sweatdaddy.controller;

import jakarta.validation.Valid;
import java.util.List;
import nl.sweatdaddy.dto.ApiResponse;
import nl.sweatdaddy.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.dto.ExerciseResponseDto;
import nl.sweatdaddy.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

  private final ExerciseService exerciseService;

  public ExerciseController(ExerciseService exerciseService) {
    this.exerciseService = exerciseService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ExerciseResponseDto>>> getExercises(
      @RequestParam(required = false) String name, @RequestParam(required = false) String muscles,
      @RequestParam(required = false) String movement) {
    List<ExerciseResponseDto> exercises;

    if (name != null && !name.isBlank()) {
      exercises = exerciseService.getByName(name); // toevoegen aan service
    } else if (muscles != null && !muscles.isBlank()) {
      exercises = exerciseService.getByMuscles(muscles);
    } else if (movement != null && !movement.isBlank()) {
      exercises = exerciseService.getByMovement(movement);
    } else {
      exercises = exerciseService.getAllExercises();
    }

    return ResponseEntity.ok(
        new ApiResponse<>(
            exercises,
            "All exercises successfully retrieved"
        )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ExerciseResponseDto>> getExercise(@PathVariable("id") Long id) {
    ExerciseResponseDto dto = exerciseService.getExerciseById(id);
    return ResponseEntity.ok(new ApiResponse<>(dto, "Exercise found"));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ExerciseResponseDto>> createExercise(
      @RequestBody @Valid CreateExerciseRequestDto request) {

    ExerciseResponseDto created = exerciseService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse<>(created, "Exercises added to library")
        );
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ExerciseResponseDto>> updateExercise(
      @PathVariable Long id, @RequestBody @Valid CreateExerciseRequestDto request) {
    ExerciseResponseDto updated = exerciseService.update(id, request); // nog maken in service
    return ResponseEntity.ok(new ApiResponse<>(updated, "Exercise has been updated"));

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<ExerciseResponseDto>> deleteExercise(@PathVariable Long id) {
    ExerciseResponseDto deleted = exerciseService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(deleted, "Exercise succesfully deleted"));
  }

}


