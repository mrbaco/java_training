package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModification extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupsPage();
    if (!app.getGroupHelper().isThereAnySelect()) {
      app.getGroupHelper().createGroup(new GroupData("new group", "header of new group", "footer of new group"));
      app.getNavigationHelper().gotoGroupsPage();
    }
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupCreationForm(new GroupData("test1", "test2", "test3"));
    app.getGroupHelper().submitGroupModificationForm();
    app.getNavigationHelper().gotoGroupsPage();
  }
}
