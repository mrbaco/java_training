package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletion extends TestBase {
  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAnySelect()) {
      app.getContactHelper().createContact(new ContactData("Denis", "Olegovich", "Sokolov", "mrbaco", "it is me, guys", "Severstal", "Cherepovets town", "89000000000", "mrbaco@ya.ru", "http://robotics-co.ru", "1", "December", "1993", "test1", "It is small note text!"), true);
      app.getNavigationHelper().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    // тестовые данные
    int entryToRemove = before.size() - 1;

    app.getContactHelper().selectElement(entryToRemove);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().approveDeletion();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    before.remove(entryToRemove);

    // проверка количества записей
    Assert.assertEquals(after.size(), before.size());

    // проверка того, что была удалена действительно нужная запись
    Assert.assertEquals(before, after);
  }
}
