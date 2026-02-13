package nl.sweatdaddy.workout.service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import nl.sweatdaddy.client.entity.Client;
import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.exercise.dto.ExerciseResponseDto;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.workout.dto.CreateWorkoutRequestDto;
import nl.sweatdaddy.workout.dto.WorkoutResponseDto;
import nl.sweatdaddy.workout.entity.Workout;
import nl.sweatdaddy.workout.repository.WorkoutRepository;
import org.hibernate.annotations.NotFound;
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

    public WorkoutResponseDto getWorkoutById(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new NotFoundException("Workout not found"));

        return toDto(workout);
    }


    // workout maken
    @Transactional
    public WorkoutResponseDto create(CreateWorkoutRequestDto request) {

        if (workoutRepository.existsByNameIgnoreCase(request.getName().trim())) {
            throw new ConflictException("Workout name '" + request.getName() + "' already exists");
        }

        // zoekt de exercise objecten op obv de ids uit de dto en checkt of er niet per ongeluk exercises
        // worden toegevoegd die niet bestaan
        List<Long> requestedIds = request.getExerciseIds();
        List<Exercise> exercises = exerciseRepository.findAllById(requestedIds);

        if (exercises.size() != requestedIds.size()) {
            List<Long> foundIds = exercises.stream().map(exercise -> exercise.getId()).toList();
            List<Long> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new NotFoundException("Exercises with id's: " + missingIds + " not found.");
        }

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
        if(!workout.getName().equalsIgnoreCase(newName)) {
            if(workoutRepository.existsByNameIgnoreCase(newName)) {
                throw new ConflictException("Workout name " + newName + " already exists");
            }
        }

        // zoekt de exercise objecten op obv de ids uit de dto en checkt of er niet per ongeluk exercises
        // worden toegevoegd die niet bestaan
        List<Long> requestedIds = request.getExerciseIds();
        List<Exercise> exercises = exerciseRepository.findAllById(requestedIds);

        if (exercises.size() != requestedIds.size()) {
            List<Long> foundIds = exercises.stream().map(exercise -> exercise.getId()).toList();
            List<Long> missingIds = requestedIds.stream().filter(reqId -> !foundIds.contains(reqId)).toList();
            throw new NotFoundException("Exercises with id's: " + missingIds + " not found.");
        }

        workout.setName(newName);
        workout.setNotes(request.getNotes());
        workout.setCreatedBy(request.getCreatedBy());
        workout.setExerciseList(exercises);

        Workout updatedWorkout = workoutRepository.save(workout);
        return toDto(updatedWorkout);
    }

    // workout verwijderen
    @Transactional
    public WorkoutResponseDto delete(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Workout with id " + id + " not found"));

        // ontkoppelen van de workouts aan clients
        for (Client client : workout.getClients()) { // Let op: voeg deze relatie toe aan Workout entity of zoek ze op via de repo
            client.getWorkoutList().remove(workout);
        }

        workoutRepository.delete(workout);

        return toDto(workout);
    }

    // mapper
    private WorkoutResponseDto toDto(Workout workout) {

        List<ExerciseResponseDto> exerciseDtos = workout.getExerciseList().stream().map(
                exercise -> new ExerciseResponseDto(exercise.getId(), exercise.getName(), exercise.getMuscles(),
                                                    exercise.getMovement())).toList();

        return new WorkoutResponseDto(workout.getId(), workout.getName(), workout.getCreatedAt(),
                                      workout.getCreatedBy(),
                                      exerciseDtos, workout.getNotes());
    }
}
