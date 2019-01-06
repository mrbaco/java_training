package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupDeletion extends TestBase {
  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGroupsPage();
    if (!app.getGroupHelper().isThereAnySelect()) {
      app.getGroupHelper().createGroup(new GroupData("new group", "header of new group", "footer of new group"));
    }
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupCreationPage();
  }
}