package nl.sweatdaddy.workout.service;

import jakarta.transaction.Transactional;

import java.util.List;

import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.exercise.dto.ExerciseResponseDto;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.workout.dto.CreateWorkoutRequestDto;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutService(WorkoutRepository workoutRepository,
                          ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<WorkoutResponseDto> getAllWorkouts() {
        return workoutRepository.findAll().stream().map(this::toDto).toList();
    }


    // workout maken
    @Transactional
    public WorkoutResponseDto create(CreateWorkoutRequestDto request) {

        if (workoutRepository.existsByNameIgnoreCase(request.getName().trim())) {
            throw new ConflictException("Workout name '" + request.getName() + "' already exists");
        }

        // zoekt de exercise objecten op obv de ids uit de dto
        List<Exercise> exercises = exerciseRepository.findAllById(request.getExerciseIds());

        // maakt de workout entity met de gevonden lijst van oefeningen

        Workout entity = new Workout(
                null,
                request.getName(),
                null,
                null,
                request.getCreatedBy(),
                exercises,
                request.getNotes()
        );

        Workout savedWorkout = workoutRepository.save(entity);
        return toDto(savedWorkout);
    }

    // Workout updaten
    @Transactional
    public WorkoutResponseDto update(Long id, CreateWorkoutRequestDto request) {
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workout with " + id + " not found"));

        String newName = request.getName().trim();

        // checken of naam al bestaat bij een van de andere workouts
        workoutRepository.findByNameIgnoreCase(newName).filter(
                        foundWorkout -> !foundWorkout.getId().equals(id))
                .ifPresent(foundWorkout -> {
                    throw new ConflictException("Workout name already exists");
                });

        List<Exercise> exercises = exerciseRepository.findAllById(request.getExerciseIds());

        workout.setName(newName);
        workout.setNotes(request.getNotes());
        workout.setExerciseList(exercises);

        Workout updatedWorkout = workoutRepository.save(workout);
        return toDto(updatedWorkout);
    }

    @Transactional
    public WorkoutResponseDto delete(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workout with id " + id + " not found"));

        workoutRepository.delete(workout);

        return toDto(workout);
    }


    // mapper
    private WorkoutResponseDto toDto(Workout workout) {

        List<ExerciseResponseDto> exerciseDtos = workout.getExerciseList().stream().map(
                exercise -> new ExerciseResponseDto(exercise.getName(), exercise.getMuscles(),
                                                    exercise.getMovement())).toList();

        return new WorkoutResponseDto(workout.getId(), workout.getName(), workout.getCreatedAt(),
                                      workout.getCreatedBy(),
                                      exerciseDtos, workout.getNotes());
    }
}
