package nl.sweatdaddy.exercise.entity;

import jakarta.persistence.*;
import nl.sweatdaddy.workout.entity.Workout;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

  private @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

  @Column(nullable = false, length = 100)
  private String name;
  @Column(nullable = false)
  private String muscles;
  @Column(nullable = false)
  private String movement;

    @ManyToMany(mappedBy = "exerciseList")
    private List<Workout> workouts = new ArrayList<>();

  protected Exercise() {
  }

  public Exercise(Long id, String name, String muscles, String movement) {
    this.id = id;
    this.name = name;
    this.muscles = muscles;
    this.movement = movement;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public void setName(String name) {
    this.name = name;
  }

  public void setMuscles(String muscles) {
    this.muscles = muscles;
  }

  public void setMovement(String movement) {
    this.movement = movement;
  }
    public List<Workout> getWorkouts() {
        return workouts;
    }
}
