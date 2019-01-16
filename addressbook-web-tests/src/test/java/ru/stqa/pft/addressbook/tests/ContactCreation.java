package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreation extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTo().contactsPage();

    Contacts before = app.contact().all();

    // тестовые данные
    ContactData contact = new ContactData().withFirstname("Denis").
                                            withMiddlename("Olegovich").
                                            withLastname("Sokolov").
                                            withNickname("mrbaco").
                                            withTitle("it is me, guys").
                                            withCompany("Severstal").
                                            withAddress("Cherepovets town").
                                            withMobile("89000000000").
                                            withEmail("mrbaco@ya.ru").
                                            withHomepage("http://robotics-co.ru").
                                            withBday("1").
                                            withBmonth("December").
                                            withByear("1993").
                                            withGroup("test1").
                                            withNotes("It is small note text!");

    app.contact().create(contact, true);

    Contacts after = app.contact().all();

    // проверка количества записей
    assertThat(after.size(), equalTo(before.size() + 1));

    // проверка того, что была добавлена нужная запись
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
