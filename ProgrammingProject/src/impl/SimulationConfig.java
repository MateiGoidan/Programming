package impl;
import models.*;
public class SimulationConfig {
    public final int initialPopulation;
    public final int personSpeed;
    public final int initialSickProbability;
    public final int cureProbability;
    public final int deathProbability;

    public SimulationConfig(int initialPopulation, int personSpeed, int initialSickProbability, int cureProbability, int deathProbability) {
        this.initialPopulation = initialPopulation;
        this.personSpeed = personSpeed;
        this.initialSickProbability = initialSickProbability;
        this.cureProbability = cureProbability;
        this.deathProbability = deathProbability;

    }
}
