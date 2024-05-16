package impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.*;

public class SimulationCanvas extends JPanel {
  private final int width;
  private final int height;

  private JFrame frame;
  private List<Person> people;

  public SimulationCanvas(int width, int height) {
    this.width = width;
    this.height = height;

    // Creating the frame for GUI
    frame = new JFrame();
    frame.setTitle("Virus Spread Simulation");
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(this, BorderLayout.CENTER);
    new Thread(this::gameLoop).start();
  }

  public void startSimulation() { frame.setVisible(true); }

  // Initializing the number of persons in the simulation
  public void initPeople(int number) {
    people = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      people.add(new Person(width, height));
    }
  }

  // TODO: Make it so that when pressing 'q' the simulation stops!
  public void gameLoop() {
    while (true) {
      repaint();
      try {
        Thread.sleep(18);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    clearBackground(g);

    // g.setColor(Color.BLACK);
    // g.drawLine(600, 0, 600, 400);

    for (Person person : people) {
      person.update(people);
      person.draw(g);
    }
  }

  private void clearBackground(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
  }
}
