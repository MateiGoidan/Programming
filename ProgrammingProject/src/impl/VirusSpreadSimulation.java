package impl;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class VirusSpreadSimulation extends JFrame {
  private final int width = 600;
  private final int height = 400;
  private final SimulationCanvas canvas = new SimulationCanvas(width, height);

  public VirusSpreadSimulation() {
    super("Virus Spread Simulation");

    setLayout(new BorderLayout());
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    add(canvas, BorderLayout.CENTER);
    canvas.initPeople(50); // init with 50 people
    new Thread(canvas::gameLoop).start();
  }
}
