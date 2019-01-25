package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class ContactGroupAttaching extends TestBase {

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
  public void testGroupAttaching() {
    // получаем список доступных групп для присвоения
    Groups groupsList = app.db().groups();

    // получаем список контактов
    Contacts contactsList = app.db().contacts();

    // выбираем контакт для присвоения группы
    ContactData modifiedContact = contactsList.iterator().next();

    // проверяем количество групп контакта
    assertThat(modifiedContact.getGroups().size(), lessThanOrEqualTo(groupsList.size()));

    // если контакту добавлено максимальное количество групп - добавляем новую и получаем ее имя для присвоения
    String groupName;

    if (modifiedContact.getGroups().size() == groupsList.size() || groupsList.size() == 0) {
      groupName = app.group().createRandomGroup();
    } else {
      // если еще есть группа, которая не присвоена контакту - получаем ее имя для присвоения
      groupName = app.contact().findUnattachedGroup(modifiedContact.getGroups(), groupsList);
    }

    // переходим на страницу с контактами
    app.goTo().contactsPage();

    // проверяем, что groupName действительно определен
    assertThat(groupName.equals(""), equalTo(false));

    // присваиваем группу контакту
    app.contact().addToGroup(modifiedContact, groupName);

    // получаем обновленный список контактов
    contactsList = app.db().contacts();

    // проверяем, что группа действительно присвоена
    assertThat(app.contact().isContactInGroup(contactsList, modifiedContact, groupName), equalTo(true));
  }
}
