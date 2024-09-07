package models;

import impl.SimulationConfig;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public class Person {
  protected Pair<Integer, Integer> paintCoords; // Coords for painting the circle
  protected Pair<Integer, Integer> centerCoords; // Coords for the center of the circle
  protected Pair<Integer, Integer> velocity; // The velocity
  protected String status; // Status: healthy, sick, cured or dead
  protected Color color; // Color: blue, red, green, black
  private final int length; // Dimension reference
  private final int height;

  private static final Random random = new Random();

  public Person(int length, int height, SimulationConfig config) {
    this.length = length;
    this.height = height;

    int x, y;
    x = random.nextInt(length - 24) + 12;
    y = random.nextInt(height - 24) + 12;
    this.paintCoords = new Pair<Integer, Integer>(x, y);

    x += 6;
    y += 6;
    this.centerCoords = new Pair<Integer, Integer>(x, y);

    x = random.nextInt(config.getPersonSpeed() * 2 + 1) -
        config.getPersonSpeed();
    y = random.nextInt(config.getPersonSpeed() * 2 + 1) -
        config.getPersonSpeed();
    this.velocity = new Pair<Integer, Integer>(x, y);

    // Ensure that the person moves at least in one direction
    while (velocity.getX() == 0 && velocity.getY() == 0) {
      velocity.setX(random.nextInt(config.getPersonSpeed() * 2 + 1) -
          config.getPersonSpeed());
      velocity.setY(random.nextInt(config.getPersonSpeed() * 2 + 1) -
          config.getPersonSpeed());
    }

    // Randomly select for the person to be sick or not
    int sickProbability = random.nextInt(100);
    if (sickProbability > config.getInitialSickProbability()) {
      status = "healthy";
      color = Color.BLUE;
    } else {
      status = "sick";
      color = Color.RED;
    }
  }

  // TODO: Should be moved in Simulation
  public void update(List<Person> people, int deathProbability,
      int cureProbability) {
    if ("dead".equals(status)) {
      return;
    }

    if (testCollision()) {
      continuousBoundaryCollision();
    }

    interactWithOthers(people, deathProbability, cureProbability);

    paintCoords.setX(paintCoords.getX() + velocity.getX());
    centerCoords.setX(centerCoords.getX() + velocity.getX());
    paintCoords.setY(paintCoords.getY() + velocity.getY());
    centerCoords.setY(centerCoords.getY() + velocity.getY());
  }

  private boolean testCollision() {
    if (centerCoords.getX() - 6 <= 0 || centerCoords.getX() + 6 >= length ||
        centerCoords.getY() - 6 <= 0 || centerCoords.getY() + 6 >= height)
      return true;

    return false;
  }

  // Continuous Collision Detection
  private void continuousBoundaryCollision() {
    /*
     * This method ensures that the circle won't go out of bounds within the
     * simulation area. It calculates the position where the ball should be for
     * the next frame and determines the exact position needed to hit the
     * boundary perfectly. This method is effective if the velocity isn't too
     * high.
     */
    float t;
    int preX, preY;
    preX = centerCoords.getX() + velocity.getX();
    preY = centerCoords.getY() + velocity.getY();

    if (preX - 6 <= 0) {
      t = (float) (0 + 6 - preX) / (float) (centerCoords.getX() - preX);
      centerCoords.setX((int) (t * centerCoords.getX() + (1 - t) * preX));
      paintCoords.setX(centerCoords.getX() - 6);
      velocity.setX(-velocity.getX());
    } else if (preX + 6 >= 600) {
      t = (float) (600 - 6 - preX) / (float) (centerCoords.getX() - preX);
      centerCoords.setX((int) (t * centerCoords.getX() + (1 - t) * preX));
      paintCoords.setX(centerCoords.getX() - 6);
      velocity.setX(-velocity.getX());
    }

    if (preY - 6 <= 0) {
      t = (float) ((0 + 6 - preY) / (float) (centerCoords.getY() - preY));
      centerCoords.setY((int) (t * centerCoords.getY() + (1 - t) * preY));
      paintCoords.setY(centerCoords.getY() - 6);
      velocity.setY(-velocity.getY());
    } else if (preY + 6 >= 400) {
      t = (float) (400 - 6 - preY) / (float) (centerCoords.getY() - preY);
      centerCoords.setY((int) (t * centerCoords.getY() + (1 - t) * preY));
      paintCoords.setY(centerCoords.getY() - 6);
      velocity.setY(-velocity.getY());
    }
  }

  private void interactWithOthers(List<Person> people, int deathProbability,
      int cureProbability) {
    for (Person other : people) {
      if (other != this && getDistance(this, other) <= 12 &&
          other.color != Color.BLACK) {
        velocity.setX(-velocity.getX());
        velocity.setY(-velocity.getY());

        adjustPositionAfterCollision(other);

        handleInfection(other, deathProbability, cureProbability);
      }
    }
  }

  private void adjustPositionAfterCollision(Person other) {
    // Gets the angle of collision
    double angle = Math.atan2(other.centerCoords.getY() - this.centerCoords.getY(),
        other.centerCoords.getX() - this.centerCoords.getX());

    // Moves the person a few pixels in the other direction so that they don't
    // get stuck
    this.paintCoords.setX((int) (this.paintCoords.getX() - 5 * Math.cos(angle)));
    this.paintCoords.setY((int) (this.paintCoords.getY() - 5 * Math.sin(angle)));
    other.paintCoords.setX(
        (int) (other.paintCoords.getX() + 5 * Math.cos(angle)));
    other.paintCoords.setY(
        (int) (other.paintCoords.getY() + 5 * Math.sin(angle)));

    // Updates center
    this.centerCoords.setX(this.paintCoords.getX() + 6);
    this.centerCoords.setY(this.paintCoords.getY() + 6);
    other.centerCoords.setX(other.paintCoords.getX() + 6);
    other.centerCoords.setY(other.paintCoords.getY() + 6);
  }

  private void handleInfection(Person other, int deathProbability,
      int cureProbability) {
    if ("sick".equals(status) || "sick".equals(other.status)) {
      if (!"cured".equals(status) && !"dead".equals(status))
        status = "sick";
      if (!"cured".equals(other.status) && !"dead".equals(other.status))
        other.status = "sick";
    }

    // Roll the dice to see if the person gets cured or die
    if ("sick".equals(status)) {
      if (random.nextInt(100) < cureProbability) {
        status = "cured";
        color = Color.GREEN;
      }
      if (random.nextInt(50) < deathProbability) {
        status = "dead";
        color = Color.BLACK;
        velocity.setX(0);
        velocity.setY(0);
      }
    }
  }

  public void draw(Graphics g) {
    if (status.equals("healthy")) {
      color = Color.BLUE;
    } else if (status.equals("sick")) {
      color = Color.RED;
    }

    g.setColor(color);
    g.fillOval(paintCoords.getX(), paintCoords.getY(), 12, 12);
  }

  private double getDistance(Person a, Person b) {
    double dx = a.paintCoords.getX() - b.paintCoords.getX();
    double dy = a.paintCoords.getY() - b.paintCoords.getY();
    return Math.sqrt(dx * dx + dy * dy);
  }

  public String getStatus() {
    return status; // Returns the actual state of the person
  }
}
