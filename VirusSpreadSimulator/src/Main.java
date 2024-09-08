import impl.SimulationCanvas;
import impl.SimulationConfig;
import javax.swing.JFrame;
import models.Menu;

public class Main {

  public static void main(String[] args) {
    Menu menu = new Menu();

    JFrame canvas = new JFrame();
    canvas.setTitle("Virus Spread Simulation");
    canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    canvas.setSize(600, 650);

    canvas.add(menu);

    canvas.setVisible(true);
  }
}
