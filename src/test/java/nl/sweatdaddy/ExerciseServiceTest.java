package nl.sweatdaddy;


import nl.sweatdaddy.common.exception.ConflictException;
import nl.sweatdaddy.exercise.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.exercise.entity.Exercise;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import nl.sweatdaddy.exercise.service.ExerciseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    ExerciseRepository exerciseRepository;

    @InjectMocks
    ExerciseService exerciseService;

    @Test
    @DisplayName("find exercise by name")
    void getExerciseByName() {
        // arrange

        Long id = 1L;
        String exerciseName = "Test Squat";

        Exercise exercise = new Exercise(id, "Test Squat", "Quadriceps", "Knee dominant");

        when(exerciseRepository.findAllByNameContainingIgnoreCase("Test Squat")).thenReturn(
                List.of(exercise));

        // act
        var dtoList = exerciseService.getByName(exerciseName);

        // assert
        assertEquals(id, dtoList.size());
        assertEquals(exerciseName, dtoList.get(0).getName());

        verify(exerciseRepository).findAllByNameContainingIgnoreCase(exerciseName);
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    @DisplayName("create exception when name exists")
    void create_withExistingName() {

        // arrange
        var request = new CreateExerciseRequestDto();
        request.setName("Squat");
        request.setMuscles("Quads");
        request.setMovement("Compound");

        when(exerciseRepository.existsByNameIgnoreCase("Squat"))
                .thenReturn(true);

        assertThrows(
                ConflictException.class,
                () -> exerciseService.create(request)
        );

        verify(exerciseRepository).existsByNameIgnoreCase("Squat");
        verify(exerciseRepository, never()).save(any());
        verifyNoMoreInteractions(exerciseRepository);
    }

    @Test
    @DisplayName("update exercise information")
    void update_success() {
        var request = new CreateExerciseRequestDto();
        request.setName("Bench Press");
        request.setMuscles("Pectoralis Major");
        request.setMovement("Horizontal Push");

        Exercise exercise = new Exercise(1L, "Bench bless", "Bench", "Test movement");

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        var dto = exerciseService.update(1L, request);

        assertEquals("Bench Press", dto.getName());

        verify(exerciseRepository).findById(1L);
        verify(exerciseRepository).save(exercise);

    }

    @Test
    @DisplayName("create and save exercise successfully")
    void createExercise_succes() {

        var request = new CreateExerciseRequestDto();
        request.setName("Romanian Deadlift");
        request.setMovement("Hip dominant");
        request.setMuscles("Hamstrings, Gluteus Maximus");

        when(exerciseRepository.existsByNameIgnoreCase("Romanian Deadlift")).thenReturn(false);

        Exercise savedExercise = new Exercise(1L, "Romanian Deadlift", "Hamstrings, Gluteus Maximus",
                                              "Hip dominant");

        when(exerciseRepository.save(any(Exercise.class))).thenReturn(savedExercise);

        var dto = exerciseService.create(request);

        assertEquals(1L, dto.getId());
        assertEquals("Romanian Deadlift", dto.getName());
        assertEquals("Hip dominant", dto.getMovement());
        assertEquals("Hamstrings, Gluteus Maximus", dto.getMuscles());


        verify(exerciseRepository).existsByNameIgnoreCase("Romanian Deadlift");
        verify(exerciseRepository).save(any(Exercise.class));
        verifyNoMoreInteractions(exerciseRepository);

    }

    @Test
    @DisplayName("Delete exercise succesfully")
    void deleteExerciseById_success() {

        Long id = 1L;

        Exercise exercise = new Exercise(id, "Squat", "Quadriceps", "Knee Dominant");

        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));

        var dto = exerciseService.delete(id);

        assertEquals(id, dto.getId());
        assertEquals("Squat", dto.getName());
        assertEquals("Quadriceps", dto.getMuscles());
        assertEquals("Knee Dominant", dto.getMovement());

        verify(exerciseRepository).findById(id);
        verify(exerciseRepository).delete(exercise);
        verifyNoMoreInteractions(exerciseRepository);

    }

}
