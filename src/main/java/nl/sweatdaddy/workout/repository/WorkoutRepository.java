package nl.sweatdaddy.workout.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nl.sweatdaddy.workout.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

  Optional<Workout> findByNameIgnoreCase(String name);
  boolean existsByNameIgnoreCase(String name);

  List<Workout>findAllByOrderByCreatedAtDesc();
  List<Workout>findAllByOrderByCreatedAtAsc();
  List<Workout>findAllByCreatedAt(LocalDateTime createdAt);

}
