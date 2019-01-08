package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreation extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupsPage();

    List<GroupData> before = app.getGroupHelper().getGroupList();

    // тестовые данные
    GroupData group = new GroupData("new group", "header of new group", "footer of new group");

    app.getGroupHelper().createGroup(group);
    app.getNavigationHelper().gotoGroupsPage();

    List<GroupData> after = app.getGroupHelper().getGroupList();

    before.add(group);

    // проверка количества записей
    Assert.assertEquals(after.size(), before.size());

    // проверка того, что была добавлена нужная запись
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }

}
