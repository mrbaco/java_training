package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreation extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupsPage();
    app.getGroupHelper().createGroup(new GroupData("new group", "header of new group", "footer of new group"));
    app.getNavigationHelper().gotoGroupsPage();
  }

}
