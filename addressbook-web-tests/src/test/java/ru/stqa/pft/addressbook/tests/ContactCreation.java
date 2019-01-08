package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreation extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> before = app.getContactHelper().getContactList();

    // тестовые данные
    ContactData contact = new ContactData("Denis", "Olegovich", "Sokolov", "mrbaco", "it is me, guys", "Severstal", "Cherepovets town", "89000000000", "mrbaco@ya.ru", "http://robotics-co.ru", "1", "December", "1993", "test1", "It is small note text!");

    app.getContactHelper().createContact(contact, true);
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    before.add(contact);

    // проверка количества записей
    Assert.assertEquals(after.size(), before.size());

    // проверка того, что была добавлена нужная запись
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}
