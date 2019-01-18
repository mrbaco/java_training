package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreation extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupsPage();

    Groups before = app.group().all();

    // тестовые данные
    GroupData group = new GroupData().withName("new group").
                                      withHeader("header of new group").
                                      withFooter("footer of new group");

    app.group().create(group);

    // проверка количества записей
    assertThat(app.group().count(), equalTo(before.size() + 1));

    Groups after = app.group().all();

    // проверка того, что была добавлена нужная запись
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
