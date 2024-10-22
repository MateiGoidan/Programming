package models;

import impl.SimulationConfig;
import java.awt.Color;

/*
 * Singleton for creating simulation where we start with just one person sick
 */

public class PatientZero extends Person {
  private static PatientZero instance;

  private PatientZero(int length, int height, SimulationConfig config) {
    super(length, height, config);
    // Ensure PatientZero is always sick
    this.status = "sick";
    this.color = Color.RED;
  }

  public static PatientZero getInstance(int length, int height, SimulationConfig config) {
    if (instance == null) {
      instance = new PatientZero(length, height, config);
    }
    return instance;
  }
}
