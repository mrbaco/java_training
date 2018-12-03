package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class GroupCreation extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    gotoGroupsPage();
    initGroupCreation();
    fillGroupCreationForm(new GroupData("new group", "Чив-чив", "Футер"));
    submitGroupCreationForm();
    returnToGroupCreationPage();
  }

}
