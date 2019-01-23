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

    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Denis").
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
                                             withNotes("It is small note text!"), true);
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();

    // тестовые данные
    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    // проверка количества записей
    assertThat(app.contact().count(), equalTo(before.size() - 1));

    Contacts after = app.db().contacts();

    // проверка того, что была удалена действительно нужная запись
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactsListInUI();
  }
}
