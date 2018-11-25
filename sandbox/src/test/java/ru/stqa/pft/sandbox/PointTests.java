package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test

  public void testPointsDistance() {
    Point p1 = new Point(5,5);
    Point p2 = new Point(5,5);
    Point p3 = new Point(5,6);

    // test true
    //Assert.assertEquals(p1.distance(p2), 0);

    // test true
    //Assert.assertEquals(p1.distance(p3), 1);

    // test false
    Assert.assertEquals(p1.distance(p3), 2);
  }
}
