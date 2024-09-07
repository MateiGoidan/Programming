package models;

public class Pair<A, B>{
  private A x;
  private B y;

  public Pair(A x, B y) {
    this.x = x;
    this.y = y;
  }

  public A getX() {
    return x;
  }

  public void setX(A x) {
    this.x = x;
  }

  public B getY() {
    return y;
  }

  public void setY(B y) {
    this.y = y;
  }
}
