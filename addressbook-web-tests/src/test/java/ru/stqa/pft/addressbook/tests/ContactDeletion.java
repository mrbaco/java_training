package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletion extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactsPage();

    if (!app.contact().isThereAnySelect()) {
      app.contact().create(new ContactData().withFirstname("Denis").
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
                                             withNotes("It is small note text!"), true);
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.contact().all();

    // тестовые данные
    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    Contacts after = app.contact().all();

    // проверка количества записей
    assertThat(after.size(), equalTo(before.size() - 1));

    // проверка того, что была удалена действительно нужная запись
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}
