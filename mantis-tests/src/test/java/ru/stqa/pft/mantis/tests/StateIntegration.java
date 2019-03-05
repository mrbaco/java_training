package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class StateIntegration extends TestBase {
  @Test
  public void testStateIntegration1() throws RemoteException, ServiceException, MalformedURLException {
    skipIfNotFixed(1);
    System.out.println("#1 - Test executed!");
  }

  @Test
  public void testStateIntegration2() throws RemoteException, ServiceException, MalformedURLException {
    skipIfNotFixed(2);
    System.out.println("#2 - Test executed!");
  }

  @Test
  public void testStateIntegration3() throws RemoteException, ServiceException, MalformedURLException {
    skipIfNotFixed(3);
    System.out.println("#3 - Test executed!");
  }
}
