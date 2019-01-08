package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModification extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupsPage();

    if (!app.getGroupHelper().isThereAnySelect()) {
      app.getGroupHelper().createGroup(new GroupData("new group", "header of new group", "footer of new group"));
      app.getNavigationHelper().gotoGroupsPage();
    }

    List<GroupData> before = app.getGroupHelper().getGroupList();

    // тестовые данные
    int entryToModify = 0;
    GroupData dataToModify = new GroupData(before.get(entryToModify).getId(), "test1", "test2", "test3");

    app.getGroupHelper().selectElement(entryToModify);
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupCreationForm(dataToModify);
    app.getGroupHelper().submitGroupModificationForm();
    app.getNavigationHelper().gotoGroupsPage();

    List<GroupData> after = app.getGroupHelper().getGroupList();

    before.remove(entryToModify);
    before.add(dataToModify);

    // проверка количества записей
    Assert.assertEquals(after.size(), before.size());

    // проверка того, что была модифицирована нужная запись
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}
