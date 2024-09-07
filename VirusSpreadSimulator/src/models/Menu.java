package models;

import impl.SimulationCanvas;
import impl.SimulationConfig;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Menu extends JPanel implements ActionListener {
  private JLabel info;
  private JSlider people;
  private JSlider speed;
  private JSlider sickProb;
  private JSlider cureProb;
  private JSlider deathProb;
  private JButton button;
  private SimulationCanvas canvasImplementation;
  private SimulationConfig canvasConfig;

  public Menu() {
    // Creating the label that will contain the title and the info for the user
    info = new JLabel("<html><h1 style='font-size: 24px;'>Virus Spread "
                      + "Simulation</h1><p style='font-size:"
                      + " 14px;'>This simulation visualizes virus spread, "
                      + "inspired by COVID-19. Select"
                      + " the number of people and the virus details before "
                      + "starting the simulation.</p>"
                      + "</html>");
    info.setHorizontalTextPosition(JLabel.CENTER);
    info.setVerticalTextPosition(JLabel.CENTER);
    info.setAlignmentX(JLabel.CENTER_ALIGNMENT);

    people = new JSlider(1, 150, 75);
    people.setPaintTicks(true);
    people.setMinorTickSpacing(25);
    people.setPaintTrack(true);
    people.setMajorTickSpacing(50);
    people.setPaintLabels(true);

    // Create a custom label table
    Hashtable<Integer, JLabel> peopleTable = new Hashtable<>();
    peopleTable.put(1, new JLabel("1"));
    peopleTable.put(50, new JLabel("50"));
    peopleTable.put(100, new JLabel("100"));
    peopleTable.put(150, new JLabel("150"));

    JLabel peopleLabel =
        new JLabel("Select the number of people: " + people.getValue());

    people.setLabelTable(peopleTable);
    people.addChangeListener(
        e
        -> peopleLabel.setText("Select the number of people: " +
                               people.getValue()));

    speed = new JSlider(1, 20, 3);
    speed.setPaintTicks(true);
    speed.setMinorTickSpacing(5);
    speed.setPaintTrack(true);
    speed.setMajorTickSpacing(10);
    speed.setPaintLabels(true);

    // Create a custom label table
    Hashtable<Integer, JLabel> speedTable = new Hashtable<>();
    speedTable.put(1, new JLabel("1"));
    speedTable.put(5, new JLabel("5"));
    speedTable.put(10, new JLabel("10"));
    speedTable.put(15, new JLabel("15"));
    speedTable.put(20, new JLabel("20"));

    JLabel speedLabel = new JLabel(
        "Select the speed amplifier for the people: " + speed.getValue());

    speed.setLabelTable(speedTable);
    speed.addChangeListener(
        e
        -> speedLabel.setText("Select the speed amplifier for the people: " +
                              speed.getValue()));

    sickProb = new JSlider(0, 100, 10);
    sickProb.setPaintTicks(true);
    sickProb.setMinorTickSpacing(10);
    sickProb.setPaintTrack(true);
    sickProb.setMajorTickSpacing(25);
    sickProb.setPaintLabels(true);

    // Create a custom label table
    Hashtable<Integer, JLabel> sickProbTable = new Hashtable<>();
    sickProbTable.put(0, new JLabel("0"));
    sickProbTable.put(25, new JLabel("25"));
    sickProbTable.put(50, new JLabel("50"));
    sickProbTable.put(75, new JLabel("75"));
    sickProbTable.put(100, new JLabel("100"));

    JLabel sickProbLabel = new JLabel(
        "Increase the chance of getting infected: " + sickProb.getValue());

    sickProb.setLabelTable(sickProbTable);
    sickProb.addChangeListener(
        e
        -> sickProbLabel.setText("Increase the chance of getting infected: " +
                                 sickProb.getValue()));

    cureProb = new JSlider(0, 100, 20);
    cureProb.setPaintTicks(true);
    cureProb.setMinorTickSpacing(10);
    cureProb.setPaintTrack(true);
    cureProb.setMajorTickSpacing(25);
    cureProb.setPaintLabels(true);

    // Create a custom label table
    Hashtable<Integer, JLabel> cureProbTable = new Hashtable<>();
    cureProbTable.put(0, new JLabel("0"));
    cureProbTable.put(25, new JLabel("25"));
    cureProbTable.put(50, new JLabel("50"));
    cureProbTable.put(75, new JLabel("75"));
    cureProbTable.put(100, new JLabel("100"));

    JLabel cureProbLabel = new JLabel("Increase the chance of getting cured: " +
                                      cureProb.getValue());

    cureProb.setLabelTable(cureProbTable);
    cureProb.addChangeListener(
        e
        -> cureProbLabel.setText("Increase the chance of getting cured: " +
                                 cureProb.getValue()));

    deathProb = new JSlider(0, 100, 20);
    deathProb.setPaintTicks(true);
    deathProb.setMinorTickSpacing(10);
    deathProb.setPaintTrack(true);
    deathProb.setMajorTickSpacing(25);
    deathProb.setPaintLabels(true);

    // Create a custom label table
    Hashtable<Integer, JLabel> deathProbTable = new Hashtable<>();
    deathProbTable.put(0, new JLabel("0"));
    deathProbTable.put(25, new JLabel("25"));
    deathProbTable.put(50, new JLabel("50"));
    deathProbTable.put(75, new JLabel("75"));
    deathProbTable.put(100, new JLabel("100"));

    JLabel deathProbLabel =
        new JLabel("Increase the chance of dying: " + deathProb.getValue());

    deathProb.setLabelTable(deathProbTable);
    deathProb.addChangeListener(
        e
        -> deathProbLabel.setText("Increase the chance of dying: " +
                                  deathProb.getValue()));

    // Creating the start simulatin button
    button = new JButton();
    button.setMaximumSize(new Dimension(200, 100));
    button.addActionListener(this);
    button.setText("Start Simulation");
    button.setBorder(BorderFactory.createEtchedBorder());
    button.setFont(new Font("Arial", Font.BOLD, 20));

    // Panle configurations
    this.setPreferredSize(new Dimension(250, 250));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(Box.createVerticalStrut(10));
    this.add(info);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(peopleLabel);
    this.add(people);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(speedLabel);
    this.add(speed);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(sickProbLabel);
    this.add(sickProb);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(cureProbLabel);
    this.add(cureProb);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(deathProbLabel);
    this.add(deathProb);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(button);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button) {
      canvasConfig = new SimulationConfig(
          people.getValue(), speed.getValue(), sickProb.getValue(),
          cureProb.getValue(),
          deathProb.getValue()); // people, speed, sick probability, cure
      // probability, dead probability
      canvasImplementation = new SimulationCanvas(600, 400, canvasConfig);
      canvasImplementation.startSimulation();
      button.setEnabled(false);
      this.setVisible(false);
    }

    throw new UnsupportedOperationException(
        "Unimplemented method 'actionPerformed'");
  }
}
