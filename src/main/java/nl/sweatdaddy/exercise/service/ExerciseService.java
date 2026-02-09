package nl.sweatdaddy.exercise.service;

import jakarta.transaction.Transactional;
import java.util.List;
import nl.sweatdaddy.exercise.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.exercise.dto.ExerciseResponseDto;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exception.ConflictException;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import nl.sweatdaddy.exception.NotFoundException;

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

  private final ExerciseRepository exerciseRepository;

  public ExerciseService(ExerciseRepository exerciseRepository) {
    this.exerciseRepository = exerciseRepository;
  }

  public List<ExerciseResponseDto> getAllExercises() {
    return exerciseRepository.findAll().stream()
        .map(e -> new ExerciseResponseDto(e.getName(), e.getMuscles(), e.getMovement())).toList();
  }

  public ExerciseResponseDto getExerciseById(Long id) {
    Exercise exercise = exerciseRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Exercise with id " + id + " not found"));

    return toDto(exercise);
  }

  public List<ExerciseResponseDto> getByName(String name) {
    return exerciseRepository.findAllByNameContainingIgnoreCase(name).stream().map(this::toDto)
        .toList();
  }

  public List<ExerciseResponseDto> getByMuscles(String muscles) {
    return exerciseRepository.findAllByMusclesContainingIgnoreCase(muscles).stream()
        .map(this::toDto)
        .toList();
  }

  public List<ExerciseResponseDto> getByMovement(String movement) {
    return exerciseRepository.findAllByMovementContainingIgnoreCase(movement).stream()
        .map(this::toDto)
        .toList();
  }

  @Transactional
  public ExerciseResponseDto create(CreateExerciseRequestDto request) {
    if (exerciseRepository.existsByNameIgnoreCase(request.getName().trim())) {
      throw new ConflictException("Exercise with " + request.getName() + " already exists");
    }

    Exercise entity = new Exercise(
        null,
        request.getName(),
        request.getMuscles(),
        request.getMovement()
    );
    Exercise saved = exerciseRepository.save(entity);
    return new ExerciseResponseDto(saved.getName(), saved.getMuscles(), saved.getMovement());
  }


  // Overweoog eerst een updateDto te maken, maar niet nodig voor dit project.
  @Transactional
  public ExerciseResponseDto update(Long id, CreateExerciseRequestDto request) {
    Exercise exercise = exerciseRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Exercise with " + id + " not found"));

    exercise.setName(request.getName());
    exercise.setMuscles(request.getMuscles());
    exercise.setMovement(request.getMovement());

    Exercise updated = exerciseRepository.save(exercise);

    return toDto(updated);
  }

  @Transactional
  public ExerciseResponseDto delete(Long id) {
    Exercise exercise = exerciseRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Can't find exercise with id: " + id));

    exerciseRepository.delete(exercise);

    return toDto(exercise);
  }

  // mapper
  private ExerciseResponseDto toDto(Exercise e) {
    return new ExerciseResponseDto(e.getName(), e.getMuscles(), e.getMovement());
  }


}
