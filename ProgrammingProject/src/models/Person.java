package models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public class Person {
  private int x, y;
  private int speedX, speedY;
  private String status;
  private Color color;
  private final int width;
  private final int height;

  private static final Random random = new Random();

  public Person(int width, int height) {
    this.width = width;
    this.height = height;

    // TODO: Mybe check if there already exists a person on that coordinate so
    // that they don't get stuck
    x = random.nextInt(width - 12);
    y = random.nextInt(height - 12);

    // TODO: Make it so that the persons speed is never 0 so that it always
    // moves
    speedX = random.nextInt(7) - 3;
    speedY = random.nextInt(7) - 3;

    int sickProbability = random.nextInt(100);
    if (sickProbability < 80) {
      status = "healthy";
      color = Color.BLUE;
    } else {
      status = "sick";
      color = Color.RED;
    }
  }

  public void update(List<Person> people) {
    x += speedX;
    y += speedY;

    boundaryCollision();
    interactWithOthers(people);
  }

  // TODO: might need to think for a new way to make collision with margins.
  // Or cap the speed for all persons.
  private void boundaryCollision() {
    if (x <= 0 || x >= width - 12)
      speedX = -speedX;
    if (y <= 0 || y >= height - 12)
      speedY = -speedY;
  }

  // TODO: this needs improvement?
  private void interactWithOthers(List<Person> people) {
    for (Person other : people) {
      if (other != this && getDistance(this, other) < 10 &&
          other.color != Color.BLACK) {
        speedX = -speedX;
        speedY = -speedY;

        // If at least one of them is sick
        if ("sick".equals(status) || "sick".equals(other.status)) {
          // If "this" person is not cured and not dead, it becomes sick
          if (!"cured".equals(status) && !"dead".equals(status))
            status = "sick";

          // If "other" person is not cured and not dead, it becomes sick
          if (!"cured".equals(other.status) && !"dead".equals(other.status))
            other.status = "sick";
        }
      }
    }

    // Roll the dice to see if the person gets cured or die
    if ("sick".equals(status)) {
      if (random.nextInt(100) < 1) {
        status = "cured";
        color = Color.GREEN;
      }
      if (random.nextInt(100) < 1) {
        status = "dead";
        color = Color.BLACK;
        speedX = 0;
        speedY = 0;
      }
    }
  }

  public void draw(Graphics g) {
    if (status.equals("healthy")) {
      color = Color.BLUE;
    } else if(status.equals("sick")){
      color = Color.RED;
    }
    g.setColor(color);
    g.fillOval(x, y, 12, 12);
  }

  private double getDistance(Person a, Person b) {
    double dx = a.x - b.x;
    double dy = a.y - b.y;
    return Math.sqrt(dx * dx + dy * dy);
  }
}
