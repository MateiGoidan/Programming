import impl.SimulationCanvas;
import impl.SimulationConfig;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    /*
     * Making the sick probability -1 will automaticaly start the simulation
     * with just one sick person
     */
    SimulationConfig config = new SimulationConfig( 50, 3, -1, 20, 20); // people, speed, sick probability, cure
                            // probability, dead probability
    SimulationCanvas simulation = new SimulationCanvas(600, 400, config);
    SwingUtilities.invokeLater(() -> {
      simulation.initPeople();
      simulation.startSimulation();
    });
  }
}
