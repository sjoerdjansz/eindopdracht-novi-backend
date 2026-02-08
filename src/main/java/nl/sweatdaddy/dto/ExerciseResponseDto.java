package nl.sweatdaddy.dto;


// Exercise data transfer object is een speciale class die we gebruiken als blauwdruk van een exercise.
// De DTO bevat alleen de data en heeft bewust geen business logica of gedrag.
// Hij wordt gebruikt om de exercise gegevens gestructureerd te houden tussen meerdere lagen van API.
// De DTO wordt op meerdere plekken gebruikt, zoals controller, service, mapper of in tests.
public class ExerciseResponseDto {

  private final String name;
  private final String muscles;
  private final String movement;

  public ExerciseResponseDto(String name, String muscles, String movement) {
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
