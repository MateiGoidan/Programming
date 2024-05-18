package impl;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.*;
import java.util.*;



public class SimulationCanvas extends JPanel {
  private final int width;
  private final int height;

  private JFrame frame;
  private List<Person> people= new ArrayList<>();
  private SimulationConfig config;
  private int healthyCount;
  private int sickCount;
  private int curedCount;
  private int deadCount;

  public SimulationCanvas(int width, int height, SimulationConfig config) {
    this.width = width;
    this.height = height;
    int totalHeight = height+60;
    this.config=config;

    setPreferredSize(new Dimension(width, totalHeight));

    // Creating the frame for GUI
    frame = new JFrame("Virus Spread Simulation");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(this, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);

  }

  public void startSimulation() {
    initPeople();
    frame.setVisible(true);
    new Thread (this:: gameLoop).start();
  }
  private boolean checkIfSickPeopleRemain() {
    for (Person person : people) {
      if ("sick".equals(person.getStatus())) {
        return true;  // There is at least one sick person
      }
    }
    return false;  // There is not one sick person
  }
  public void stopSimulation() {
    System.out.println("Simulation ended: No sick people remaining.");
    // Ends the simulation after having no sick people
  }


  // Initializing the number of persons in the simulation
  public void initPeople() {
    people = new ArrayList<>();
    for (int i = 0; i < config.initialPopulation; i++) {
      people.add(new Person(width, height, config));
    }
  }

  // TODO: Make it so that when pressing 'q' the simulation stops!
  public void gameLoop() {
    while (true) {
      if (!checkIfSickPeopleRemain()) {
        stopSimulation();
        break;  // Sale del bucle si no quedan enfermos
      }
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

    for (Person person : people) {
      person.update(people);
      person.draw(g);
    }
    updateCounts();
    g.setColor(Color.BLACK);
    g.drawString("Healthy people: " + healthyCount, 10, height+10);
    g.drawString("Sick people: " + sickCount, 10, height+25);
    g.drawString("Cured people: " + curedCount, 10, height+40);
    g.drawString("Dead people: " + deadCount, 10, height+55);
  }

  private void clearBackground(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
  }
  private void updateCounts(){
    healthyCount = 0;
    sickCount =0;
    curedCount=0;
    deadCount=0;
    for (Person person: people){
      switch (person.getStatus()){
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
