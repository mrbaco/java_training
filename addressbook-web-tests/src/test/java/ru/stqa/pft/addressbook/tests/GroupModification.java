package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModification extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupsPage();

    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("new group").
                                         withHeader("header of new group").
                                         withFooter("footer of new group"));
    }
  }

  @Test
  public void testGroupModification() {
    Groups before = app.db().groups();

    // тестовые данные
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifiedGroup.getId()).
                                      withName("test1 modified").
                                      withHeader("test2 modified").
                                      withFooter("test3 modified");

    app.group().modify(group);

    // проверка количества записей
    assertThat(app.group().count(), equalTo(before.size()));

    Groups after = app.db().groups();

    // проверка того, что была модифицирована нужная запись
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    verifyGroupsListInUI();
  }
}
