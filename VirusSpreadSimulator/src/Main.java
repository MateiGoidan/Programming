import impl.SimulationCanvas;
import impl.SimulationConfig;
import javax.swing.JFrame;
import models.Menu;

// TODO: The project should have 3 main classes:
//  - The Person class that has methods of changing the position and state of an
//  Person object, and
// not anything else that is not neccesary;
//  - The Canvas class that creates the window and preaty much takes care of the
//  interface of our
// project;
//  - And the Config class (maybe give a different name) that implements the
//  simulation using the
// two classes;

public class Main {

  public static void main(String[] args) {
    /*
     * Making the sick probability -1 will automaticaly start the simulation
     * with just one sick person
     */
    // SimulationConfig config = new SimulationConfig(
    //     50, 3, 0, 20, 20); // people, speed, sick probability, cure
    //
    // SimulationCanvas simulation =
    //     new SimulationCanvas(600, 400, config); // probability, dead probability

    Menu menu = new Menu();

    JFrame canvas = new JFrame();
    canvas.setTitle("Virus Spread Simulation");
    canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    canvas.setSize(600, 650);

    canvas.add(menu);

    canvas.setVisible(true);

    // SwingUtilities.invokeLater(() -> {
    // simulation.startSimulation();
    // });
  }
}
