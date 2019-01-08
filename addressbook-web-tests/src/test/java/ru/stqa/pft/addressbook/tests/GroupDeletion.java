package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletion extends TestBase {
  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGroupsPage();

    if (!app.getGroupHelper().isThereAnySelect()) {
      app.getGroupHelper().createGroup(new GroupData("new group", "header of new group", "footer of new group"));
      app.getNavigationHelper().gotoGroupsPage();
    }

    List<GroupData> before = app.getGroupHelper().getGroupList();

    // тестовые данные
    int entryToRemove = before.size() - 1;

    app.getGroupHelper().selectElement(entryToRemove);
    app.getGroupHelper().deleteSelectedGroups();
    app.getNavigationHelper().gotoGroupsPage();

    List<GroupData> after = app.getGroupHelper().getGroupList();

    before.remove(entryToRemove);

    // проверка количества записей
    Assert.assertEquals(after.size(), before.size());

    // проверка того, что была удалена действительно нужная запись
    Assert.assertEquals(before, after);
  }
}