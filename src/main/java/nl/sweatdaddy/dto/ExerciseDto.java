package nl.sweatdaddy.dto;

public class ExerciseDto {

  private final String name;
  private final String muscles;
  private final String movement;

  public ExerciseDto(String name, String muscles, String movement) {
    this.name = name;
    this.muscles = muscles;
    this.movement = movement;
  }

  public String getName() {
    return name;
  }

  public String getMuscles() {
    return muscles;
  }

  public String getMovement() {
    return movement;
  }
}
