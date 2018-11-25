package ru.stqa.pft.sandbox;

public class my {
  public static void main(String[] args) {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(1, 1);

    System.out.println("-- ФУНКЦИЯ --");
    System.out.println("Расстояние между двумя точками: " + distance(p1, p2));

    System.out.println("-- МЕТОД --");
    System.out.println("Расстояние между двумя точками: " + p1.distance(p2));
    System.out.println("Расстояние между двумя точками (alt): " + p2.distance(p1));
  }

  public static double distance(Point p1, Point p2) {
    double var = (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    return Math.sqrt(var);
  }
}