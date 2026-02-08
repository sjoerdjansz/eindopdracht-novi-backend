package nl.sweatdaddy.service;

import java.util.List;
import nl.sweatdaddy.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.dto.ExerciseResponseDto;
import org.springframework.stereotype.Service;
/* De service bevat de business logica.
* Op de informatie (oefeningen) kan de business logica worden losgelaten, zoals:
* beslissingen van wat wel en niet kan, validaties, samenvoegen van data, regels zoals dat een oefening
* maar één movement mag bevatten.
* De service fungeert als tussenlaag tussen de controller en de repository. De controller handelt het
* HTTP verkeer af terwijl de service bepaalt hoe de data wordt opgehaald en (bijv.) wordt gecombineerd.
* Let op: in deze fase wordt gebruikgemaakt van mock data, maar in een latere fase zal de service
* communiceren met de repository layer van de applicatie om data uit de db te halen.
* De service layer valt tussen de controller en de repository laag.
* */
@Service
public class ExerciseService {
  public List<ExerciseResponseDto> getAll() {
    return List.of(
        new ExerciseResponseDto(
            "Back Squat",
            "Quadriceps, Glutes, Hamstrings",
            "Squat"
        ),
        new ExerciseResponseDto(
            "Front Squat",
            "Quadriceps, Core",
            "Squat"
        ),
        new ExerciseResponseDto(
            "Bench Press",
            "Chest, Triceps, Shoulders",
            "Horizontal Push"
        ),
        new ExerciseResponseDto(
            "Deadlift",
            "Hamstrings, Glutes, Lower Back",
            "Hip Hinge"
        ),
        new ExerciseResponseDto(
            "Overhead Press",
            "Shoulders, Triceps, Upper Chest",
            "Vertical Push"
        ),
        new ExerciseResponseDto(
            "Pull-Up",
            "Lats, Upper Back, Biceps",
            "Vertical Pull"
        ),
        new ExerciseResponseDto(
            "Barbell Row",
            "Upper Back, Lats, Biceps",
            "Horizontal Pull"
        ),
        new ExerciseResponseDto(
            "Romanian Deadlift",
            "Hamstrings, Glutes",
            "Hip Hinge"
        ),
        new ExerciseResponseDto(
            "Lunge",
            "Quadriceps, Glutes",
            "Single-Leg"
        ),
        new ExerciseResponseDto(
            "Push-Up",
            "Chest, Triceps, Core",
            "Horizontal Push"
        )
    );
  }

  public ExerciseResponseDto create(CreateExerciseRequestDto request) {


    if(request.getName() instanceof String) {
      System.out.println(request.getName());

    }

    return new ExerciseResponseDto(
        request.getName(),
        request.getMovement(),
        request.getMuscles()
    );
  };

}
