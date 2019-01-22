package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;

public class ContactModification extends TestBase {
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
                                             withGroup("test1").
                                             withNotes("It is small note text!"), true);
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();

    // тестовые данные
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).
                                            withFirstname("Pavel").
                                            withMiddlename("Sergeevich").
                                            withLastname("Konovalov").
                                            withNickname("fra000").
                                            withTitle("it is me, guys").
                                            withCompany("Severstal").
                                            withAddress("Cherepovets town").
                                            withMobilePhone("89000000000").
                                            withEmail1("fra777@pochta.ru").
                                            withHomepage("http://sk-grand.ru").
                                            withBday("19").
                                            withBmonth("March").
                                            withByear("1994").
                                            withGroup("test1").
                                            withNotes("It is small note text!");

    app.contact().modify(contact);

    // проверка количества записей
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.db().contacts();

    // проверка того, что была модифицирована нужная запись
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactsListInUI();
  }
}
