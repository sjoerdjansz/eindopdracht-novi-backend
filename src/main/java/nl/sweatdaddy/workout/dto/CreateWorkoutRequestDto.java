package nl.sweatdaddy.workout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class CreateWorkoutRequestDto {

  @NotBlank(message = "Workout name is required")
  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  private String name;
  @NotEmpty(message = "Author name is required")
  @Size(min = 3, max = 100, message = "Created by field is required")
  private String createdBy;
  @Size(min = 3, max = 255, message = "Notes must be between 3 and 255 characters")
  private String notes;
  @NotNull(message = "Workout needs to have exercises")
  private List<Long> exerciseIds;

  public String getName() {
    return name;
  }


  public String getCreatedBy() {
    return createdBy;
  }

  public String getNotes() {
    return notes;
  }

  public List<Long> getExerciseIds() {
    return exerciseIds;
  }
}
