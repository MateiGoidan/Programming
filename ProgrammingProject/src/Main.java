import impl.*;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    VirusSpreadSimulation simulation = new VirusSpreadSimulation();

    SwingUtilities.invokeLater(() -> simulation.setVisible(true));
  }
}
