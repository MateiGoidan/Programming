import javax.swing.SwingUtilities;
import impl.*;

public class Main {

  public static void main(String[] args) {
    SimulationConfig config = new SimulationConfig(50, 3, 5, 10, 5); // people, speed, sick probability, cure
                                                                     // probability, dead probability
    SimulationCanvas simulation = new SimulationCanvas(600, 400, config);
    SwingUtilities.invokeLater(() -> {
      simulation.initPeople();
      simulation.startSimulation();
    });
  }
}
