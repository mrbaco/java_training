package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModification extends TestBase {
  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();

    if (!app.getContactHelper().isThereAnySelect()) {
      app.getContactHelper().createContact(new ContactData("Denis", "Olegovich", "Sokolov", "mrbaco", "it is me, guys", "Severstal", "Cherepovets town", "89000000000", "mrbaco@ya.ru", "http://robotics-co.ru", "1", "December", "1993", "test1", "It is small note text!"), true);
      app.getNavigationHelper().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    // тестовые данные
    int entryToModify = 0;
    ContactData dataToModify = new ContactData("Pavel", "Sergeevich", "Konovalov", "fra000", "it is me, guys", "Severstal", "Cherepovets town", "89000000000", "fra777@pochta.ru", "http://sk-grand.ru", "19", "March", "1994", null, "It is small note text!");

    app.getContactHelper().initContactModification(entryToModify);
    app.getContactHelper().fillContactForm(dataToModify, false);
    app.getContactHelper().submitContactModificationForm();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    before.remove(entryToModify);
    before.add(dataToModify);

    // проверка количества записей
    Assert.assertEquals(after.size(), before.size());

    // проверка того, что была модифицирована нужная запись
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());

    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}
