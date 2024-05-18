package models;

import impl.SimulationConfig;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public class Person {
  private int center[];
  private int x, y, centerX, centerY;
  private int velocityX, velocityY;
  private String status;
  private Color color;
  private final int length;
  private final int height;

  private static final Random random = new Random();

  public Person(int length, int height, SimulationConfig config) {
    this.length = length;
    this.height = height;

    // TODO: Mybe check if there already exists a person on that coordinate so
    // that they don't get stuck
    x = random.nextInt(length - 24) + 12;
    y = random.nextInt(height - 24) + 12;

    centerX = x + 6;
    centerY = y + 6;
    center = new int[2];
    /* I don't know why this works. When we construct the "oval", x and y are
    the top-left most point of the oval. By that logic in order to find the
    center coordinates we should substract the radius of our circle but it
    doesn't work. If someone can figure this one out pleas tell me! */

    // Generating the speed
    velocityX = random.nextInt(config.personSpeed* 2 +1) - config.personSpeed;
    velocityY = random.nextInt(config.personSpeed* 2 +1) - config.personSpeed;
    // Ensure that the person moves at least in one direction
    while (velocityX == 0 && velocityY == 0) {
      velocityX = random.nextInt(config.personSpeed* 2 +1) - config.personSpeed;
      velocityY = random.nextInt(config.personSpeed* 2 +1) - config.personSpeed;
    }

    // Randomly select for the person to be sick or not
    int sickProbability = random.nextInt(100);
    if (sickProbability < config.initialSickProbability) {
      status = "healthy";
      color = Color.BLUE;
    } else {
      status = "sick";
      color = Color.RED;
    }
  }

  public void update(List<Person> people) {
    if ("dead".equals(status)){
      return;
    }
    if (testCollision()) {
      continuousBoundaryCollision();
    }

    interactWithOthers(people);

    x += velocityX;
    centerX += velocityX;
    y += velocityY;
    centerY += velocityY;
  }

  private boolean testCollision() {
    if (centerX - 6 <= 0 || centerX + 6 >= length || centerY - 6 <= 0 ||
        centerY + 6 >= height)
      return true;

    return false;
  }

  // Continuous Collision Detection
  private void continuousBoundaryCollision() {
    float t;
    int preX, preY;

    preX = centerX + velocityX;
    preY = centerY + velocityY;

    if (preX - 6 <= 0) {
      t = (float)(0 + 6 - preX) / (float)(centerX - preX);
      centerX = (int)(t * centerX + (1 - t) * preX);
      x = centerX - 6;
      velocityX = -velocityX;
    } else if (preX + 6 >= 600) {
      t = (float)(600 - 6 - preX) / (float)(centerX - preX);
      centerX = (int)(t * centerX + (1 - t) * preX);
      x = centerX - 6;
      velocityX = -velocityX;
    }

    if (preY - 6 <= 0) {
      t = (float)((0 + 6 - preY) / (float)(centerY - preY));
      centerY = (int)(t * centerY + (1 - t) * preY);
      y = centerY - 6;
      velocityY = -velocityY;
    } else if (preY + 6 >= 400) {
      t = (float)(400 - 6 - preY) / (float)(centerY - preY);
      centerY = (int)(t * centerY + (1 - t) * preY);
      y = centerY - 6;
      velocityY = -velocityY;
    }
  }

  // TODO: this needs improvement?
  private void interactWithOthers(List<Person> people) {
    for (Person other : people) {
      if (other != this && getDistance(this, other) <= 12 &&
              other.color != Color.BLACK) {
        velocityX = -velocityX;
        velocityY = -velocityY;

        adjustPositionAfterCollision(other);

        handleInfection(other);
      }
    }
  }
  private void adjustPositionAfterCollision(Person other) {
    // Calcular el ángulo de la colisión
    double angle = Math.atan2(other.centerY - this.centerY, other.centerX - this.centerX);

    // Mover a cada persona en la dirección opuesta unos pocos píxeles
    this.x -= 5 * Math.cos(angle);
    this.y -= 5 * Math.sin(angle);
    other.x += 5 * Math.cos(angle);
    other.y += 5 * Math.sin(angle);

    // Actualizar centro
    this.centerX = this.x + 6;
    this.centerY = this.y + 6;
    other.centerX = other.x + 6;
    other.centerY = other.y + 6;
  }
  private void handleInfection(Person other) {
    if ("sick".equals(status) || "sick".equals(other.status)) {
      if (!"cured".equals(status) && !"dead".equals(status))
        status = "sick";
      if (!"cured".equals(other.status) && !"dead".equals(other.status))
        other.status = "sick";
    }

    int cureChance = 20;
    int deathChance = 2;
    // Roll the dice to see if the person gets cured or die
    if ("sick".equals(status)) {
      if (random.nextInt(100) < cureChance) {
        status = "cured";
        color = Color.GREEN;
      }
      if (random.nextInt(50) < deathChance) {
        status = "dead";
        color = Color.BLACK;
        velocityX = 0;
        velocityY = 0;
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
    g.fillOval(x, y, 12, 12);
  }

  private double getDistance(Person a, Person b) {
    double dx = a.x - b.x;
    double dy = a.y - b.y;
    return Math.sqrt(dx * dx + dy * dy);
  }
  public String getStatus() {
    return status;  // Retorna el estado actual de la persona
  }
}
