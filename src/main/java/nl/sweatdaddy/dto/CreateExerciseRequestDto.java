package nl.sweatdaddy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateExerciseRequestDto {

  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  private String name;
  @NotBlank(message = "Muscle is required")
  private String muscles;
  @NotBlank(message = "Movement is required")
  private String movement;

  public String getName() {
    return name;
  }

  public String getMuscles() {
    return muscles;
  }

  public String getMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "Created exercise request dto: "
        + "name='" + name + '\'' +
        ", muscles='" + muscles + '\'' +
        ", movement='" + movement + '\'' +
        '}';
  }

}
