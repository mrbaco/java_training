package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreation extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupsPage();
    app.getGroupHelper().initGroupCreation();
    app.getGroupHelper().fillGroupCreationForm(new GroupData("new group", "Чив-чив", "Футер"));
    app.getGroupHelper().submitGroupCreationForm();
    app.getGroupHelper().returnToGroupCreationPage();
  }

}
