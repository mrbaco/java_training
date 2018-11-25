package ru.stqa.pft.sandbox;

public class Point {
  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point pair) {
    double var = (this.x - pair.x) * (this.x - pair.x) + (this.y - pair.y) * (this.y - pair.y);
    return Math.sqrt(var);
  }
}
