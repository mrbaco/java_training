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

public class ContactGroupDetaching extends TestBase {

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
  public void testGroupDetaching() {
    // получаем список контактов
    Contacts contactsList = app.db().contacts();

    // выбираем контакт с присвоенной группой (группами)
    ContactData modifiedContact = null;

    for (ContactData c : contactsList) {
      if (c.getGroups().size() > 0) {
        modifiedContact = c;
        break;
      }
    }

    // если контакт с группой (группами) выбран - получаем имя удаляем группы, если нет - добавляем любую группу и работаем с ней
    String groupName;

    if (modifiedContact != null) {
      groupName = modifiedContact.getGroups().iterator().next().getName();
    } else {
      Groups groupsList = app.db().groups();

      // если группы отсутствуют - создаем одну
      if (groupsList.size() == 0) groupName = app.group().createRandomGroup();
      else groupName = groupsList.iterator().next().getName();

      modifiedContact = contactsList.iterator().next();
      app.contact().addToGroup(modifiedContact, groupName);

      modifiedContact.withGroup(new GroupData().withName(groupName));
    }

    // переходим на страницу с контактами
    app.goTo().contactsPage();

    // удаляем групп
    app.contact().deleteFromGroup(modifiedContact, groupName);

    // получаем обновленный список контактов
    contactsList = app.db().contacts();

    // проверяем, что группа действительно удалена
    assertThat(app.contact().isContactInGroup(contactsList, modifiedContact, groupName), equalTo(false));
  }
}
