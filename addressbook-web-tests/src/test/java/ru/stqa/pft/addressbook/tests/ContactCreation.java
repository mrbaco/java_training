package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreation extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTo().contactsPage();

    Contacts before = app.contact().all();

    File photo = new File("src/test/resources/pic.png");

    // тестовые данные
    ContactData contact = new ContactData().withFirstname("Denis").
                                            withMiddlename("Olegovich").
                                            withLastname("Sokolov").
                                            withNickname("mrbaco").
                                            withTitle("it is me, guys").
                                            withCompany("Severstal").
                                            withAddress("Cherepovets town").
                                            withMobilePhone("89000000000").
                                            withEmail1("mrbaco@ya.ru").
                                            withHomepage("http://robotics-co.ru").
                                            withBday("1").
                                            withBmonth("December").
                                            withByear("1993").
                                            withGroup("test1").
                                            withPhoto(photo).
                                            withNotes("It is small note text!");

    app.contact().create(contact, true);

    // проверка количества записей
    assertThat(app.contact().count(), equalTo(before.size() + 1));

    Contacts after = app.contact().all();

    // проверка того, что была добавлена нужная запись
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}
