package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletion extends TestBase {
  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGroupsPage();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupCreationPage();
  }
}