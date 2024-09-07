package impl;

public class SimulationConfig {
  private final int initialPopulation;
  private final int personSpeed;
  private final int initialSickProbability;
  private final int cureProbability;
  private final int deathProbability;

  public SimulationConfig(int initialPopulation, int personSpeed,
                          int initialSickProbability, int cureProbability,
                          int deathProbability) {
    this.initialPopulation = initialPopulation;
    this.personSpeed = personSpeed;
    this.initialSickProbability = initialSickProbability;
    this.cureProbability = cureProbability;
    this.deathProbability = deathProbability;
  }

  public int getInitialPopulation() { return initialPopulation; }

  public int getPersonSpeed() { return personSpeed; }

  public int getInitialSickProbability() { return initialSickProbability; }

  public int getCureProbability() { return cureProbability; }

  public int getDeathProbability() { return deathProbability; }
}
