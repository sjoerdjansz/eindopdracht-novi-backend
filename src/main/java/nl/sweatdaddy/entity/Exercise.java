package nl.sweatdaddy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

}
