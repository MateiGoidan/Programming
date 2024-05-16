import impl.SimulationCanvas;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    SimulationCanvas simulation = new SimulationCanvas(600, 400);

    simulation.initPeople(3);

    SwingUtilities.invokeLater(() -> simulation.startSimulation());
  }
}
