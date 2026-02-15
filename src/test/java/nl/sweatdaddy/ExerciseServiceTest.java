package nl.sweatdaddy;

import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.exercise.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.exercise.service.ExerciseService;
import nl.sweatdaddy.workout.entity.Workout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    ExerciseRepository exerciseRepository;

    @InjectMocks
    ExerciseService exerciseService;

    @Test
    void getAllExercises_success() {
        Exercise exercise = new Exercise(1L, "Squat", "Quads", "Knee dominant");

        when(exerciseRepository.findAll()).thenReturn(List.of(exercise));

        var dtoList = exerciseService.getAllExercises();

        assertEquals(1, dtoList.size());
        assertEquals("Squat", dtoList.get(0).getName());

        verify(exerciseRepository).findAll();
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void getExerciseById_success() {
        Long id = 1L;
        Exercise exercise = new Exercise(id, "Squat", "Quads", "Knee dominant");

        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));

        var dto = exerciseService.getExerciseById(id);

        assertEquals(id, dto.getId());

        verify(exerciseRepository).findById(id);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void getExerciseById_notFound() {
        Long id = 999L;
        when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exerciseService.getExerciseById(id));

        verify(exerciseRepository).findById(id);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void getExerciseByName() {
        Long id = 1L;
        String exerciseName = "Test Squat";

        Exercise exercise = new Exercise(id, "Test Squat", "Quadriceps", "Knee dominant");

        when(exerciseRepository.findAllByNameContainingIgnoreCase(exerciseName))
                .thenReturn(List.of(exercise));

        var dtoList = exerciseService.getByName(exerciseName);

        assertEquals(1, dtoList.size());
        assertEquals(exerciseName, dtoList.get(0).getName());

        verify(exerciseRepository).findAllByNameContainingIgnoreCase(exerciseName);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void getExerciseByMuscles_success() {
        String muscles = "Quads";
        Exercise exercise = new Exercise(1L, "Squat", "Quads", "Knee dominant");

        when(exerciseRepository.findAllByMusclesContainingIgnoreCase(muscles))
                .thenReturn(List.of(exercise));

        var dtoList = exerciseService.getByMuscles(muscles);

        assertEquals(1, dtoList.size());

        verify(exerciseRepository).findAllByMusclesContainingIgnoreCase(muscles);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void getExerciseByMovement_success() {
        String movement = "Knee";
        Exercise exercise = new Exercise(1L, "Squat", "Quads", "Knee dominant");

        when(exerciseRepository.findAllByMovementContainingIgnoreCase(movement))
                .thenReturn(List.of(exercise));

        var dtoList = exerciseService.getByMovement(movement);

        assertEquals(1, dtoList.size());

        verify(exerciseRepository).findAllByMovementContainingIgnoreCase(movement);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void createExercise_success() {
        var request = new CreateExerciseRequestDto();
        request.setName("Romanian Deadlift");
        request.setMovement("Hip dominant");
        request.setMuscles("Hamstrings");

        when(exerciseRepository.existsByNameIgnoreCase("Romanian Deadlift")).thenReturn(false);

        Exercise savedExercise = new Exercise(1L, "Romanian Deadlift", "Hamstrings", "Hip dominant");
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(savedExercise);

        var dto = exerciseService.create(request);

        assertEquals(1L, dto.getId());

        verify(exerciseRepository).existsByNameIgnoreCase("Romanian Deadlift");
        verify(exerciseRepository).save(any(Exercise.class));
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void create_withExistingName() {
        var request = new CreateExerciseRequestDto();
        request.setName("Squat");
        request.setMuscles("Quads");
        request.setMovement("Compound");

        when(exerciseRepository.existsByNameIgnoreCase("Squat")).thenReturn(true);

        assertThrows(ConflictException.class, () -> exerciseService.create(request));

        verify(exerciseRepository).existsByNameIgnoreCase("Squat");
        verify(exerciseRepository, never()).save(any());
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void update_success() {
        Long id = 1L;

        var request = new CreateExerciseRequestDto();
        request.setName("Bench Press");
        request.setMuscles("Chest");
        request.setMovement("Horizontal Push");

        Exercise exercise = new Exercise(id, "Old name", "Old muscles", "Old movement");

        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        var dto = exerciseService.update(id, request);

        assertEquals("Bench Press", dto.getName());

        verify(exerciseRepository).findById(id);
        verify(exerciseRepository).save(exercise);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void update_notFound() {
        Long id = 999L;
        var request = new CreateExerciseRequestDto();
        request.setName("Anything");
        request.setMuscles("Anything");
        request.setMovement("Anything");

        when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exerciseService.update(id, request));

        verify(exerciseRepository).findById(id);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    void deleteExercise_success_withWorkoutsLoop() {
        Long id = 1L;

        Exercise exercise = new Exercise(id, "Squat", "Quadriceps", "Knee Dominant");

        Workout workout = mock(Workout.class);
        when(workout.getExerciseList()).thenReturn(new ArrayList<>(List.of(exercise)));

        exercise.setWorkouts(List.of(workout));

        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));

        var dto = exerciseService.delete(id);

        assertEquals(id, dto.getId());

        verify(exerciseRepository).findById(id);
        verify(exerciseRepository).delete(exercise);
        verify(workout).getExerciseList();
        verifyNoMoreInteractions(exerciseRepository, workout);
    }

    @Test
    void deleteExercise_notFound() {
        Long id = 999L;
        when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exerciseService.delete(id));

        verify(exerciseRepository).findById(id);
        verifyNoMoreInteractions(exerciseRepository);
    }
}
