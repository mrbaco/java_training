package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreation extends TestBase {
  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    String json = "";

    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }

      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());

      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
    Contacts before = app.db().contacts();

    // проверяем, существует ли группа для нового контакта. Если нет - создаем
    String checkGroupName = contact.getGroups().iterator().next().getName();

    if (!app.db().isGroup(checkGroupName)) {
      app.goTo().groupsPage();
      app.group().create(new GroupData().withName(checkGroupName).withHeader("auto group head").withFooter("auto group foot"));
    }

    app.goTo().contactsPage();

    app.contact().create(contact, true);

    // проверка количества записей
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.db().contacts();

    // проверка того, что была добавлена нужная запись
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    verifyContactsListInUI();
  }
}
