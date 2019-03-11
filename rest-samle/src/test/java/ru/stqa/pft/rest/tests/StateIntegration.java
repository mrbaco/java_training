package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;

import java.io.IOException;

public class StateIntegration extends TestBase {

  public StateIntegration() throws IOException {
  }

  @Test
  public void testStateIntegration1() throws IOException {
    skipIfNotFixed(914);
    System.out.println("#1 - Test executed!");
  }

  @Test
  public void testStateIntegration2() throws IOException {
    skipIfNotFixed(916);
    System.out.println("#2 - Test executed!");
  }

  @Test
  public void testStateIntegration3() throws IOException {
    skipIfNotFixed(917);
    System.out.println("#3 - Test executed!");
  }
}
