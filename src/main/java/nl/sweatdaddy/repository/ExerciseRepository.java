package nl.sweatdaddy.repository;

import java.util.List;
import java.util.Optional;
import nl.sweatdaddy.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

// Verantwoordelijkheden van de repository zijn:
// data toegang, crud operaties naar de database
// query optimalisatie, naast de basis handelingen kan de repo laag ook aangepaste queries definen om de hoeveelhgeid data die wordt
// verwerkt en verstuurd te verminderen
// Data abstractie: repo's maken de data abstractie toegankelijk zonder dat de service laag zich
// zorgen hoeft te maken over de onderliggende db structuren. De repo laag zorgt gewoon dat we de db
// kunnen aanspreken met normale java code ipv sql.

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  // evt. later integreren
  Optional<Exercise> findByNameIgnoreCase(String name);
  // gebruiken voor duplicate checks
  boolean existsByNameIgnoreCase(String name);
  List<Exercise> findAllByNameContainingIgnoreCase(String name);
  List<Exercise> findAllByMusclesContainingIgnoreCase(String muscles);
  List<Exercise> findAllByMovementContainingIgnoreCase(String movement);
}
