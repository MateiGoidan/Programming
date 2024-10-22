package impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.PatientZero;
import models.Person;

public class SimulationCanvas extends JPanel {
  private final int width;
  private final int height;
  private final SimulationConfig config;

  private JFrame frame;
  private List<Person> people = new ArrayList<>();
  private int healthyCount;
  private int sickCount;
  private int curedCount;
  private int deadCount;
  private long startTime;

  public SimulationCanvas(int width, int height, SimulationConfig config) {
    this.width = width;
    this.height = height;
    this.config = config;

    int totalHeight = height + 60;
    setPreferredSize(new Dimension(width, totalHeight));

    // Creating the frame for GUI
    // TODO: Add an icon
    frame = new JFrame("Virus Spread Simulation"); // set the title of the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of the application
    frame.setLayout(new BorderLayout());
    frame.add(this, BorderLayout.CENTER);
    frame.pack();
    // frame.setVisible(true);
  }

  public void startSimulation() {
    startTime = System.currentTimeMillis();

    initPeople();

    frame.setVisible(true);
    new Thread(this::gameLoop).start();
  }

  private boolean checkIfSickPeopleRemain() {
    for (Person person : people) {
      if ("sick".equals(person.getStatus())) {
        return true; // There is at least one sick person
      }
    }
    return false; // There is not one sick person
  }

  public void stopSimulation() {
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    System.out.println("Simulation ended: No sick people remaining.");
    // Ends the simulation after having no sick people
    System.out.println("Duration of simulation: " + (duration / 1000.0) +
                       " seconds");
  }

  // Initializing the number of persons in the simulation
  public void initPeople() {
    people = new ArrayList<>();
    for (int i = 0; i < config.getInitialPopulation(); i++) {
      people.add(new Person(width, height, config));
    }

    if (config.getInitialSickProbability() == 0) {
      PatientZero patienZero = PatientZero.getInstance(width, height, config);
      people.add(patienZero);
    }
  }

  public void gameLoop() {
    while (true) {
      if (!checkIfSickPeopleRemain()) {
        stopSimulation();
        Thread.interrupted();
        break; // Gets out of the loop if there is no sick people remaining
      }
      repaint();
      try {
        Thread.sleep(18);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  // TODO: should be moved in Canvas
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    clearBackground(g);

    for (Person person : people) {
      if (person instanceof PatientZero) {
        person.update(people, config.getDeathProbability(), 0);
      } else {
        person.update(people, config.getDeathProbability(),
                      config.getCureProbability());
      }
      person.draw(g);
    }
    updateCounts();
    g.setColor(Color.BLACK);
    g.drawString("Healthy people: " + healthyCount, 10, height + 10);
    g.drawString("Sick people: " + sickCount, 10, height + 25);
    g.drawString("Cured people: " + curedCount, 10, height + 40);
    g.drawString("Dead people: " + deadCount, 10, height + 55);
  }

  // TODO: should be moved in Canvas
  private void clearBackground(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
  }

  private void updateCounts() {
    healthyCount = 0;
    sickCount = 0;
    curedCount = 0;
    deadCount = 0;
    for (Person person : people) {
      switch (person.getStatus()) {
      case "healthy":
        healthyCount++;
        break;
      case "sick":
        sickCount++;
        break;
      case "cured":
        curedCount++;
        break;
      case "dead":
        deadCount++;
        break;
      }
    }
  }
}
