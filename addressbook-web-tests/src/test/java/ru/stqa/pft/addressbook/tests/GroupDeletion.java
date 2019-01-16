package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletion extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupsPage();

    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("new group").
                                         withHeader("header of new group").
                                         withFooter("footer of new group"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Groups before = app.group().all();

    // тестовые данные
    GroupData deletedGroup = before.iterator().next();

    app.group().delete(deletedGroup);

    Groups after = app.group().all();

    // проверка количества записей
    assertThat(after.size(), equalTo(before.size() - 1));

    // проверка того, что была удалена действительно нужная запись
    assertThat(after, equalTo(before.without(deletedGroup)));
  }
}