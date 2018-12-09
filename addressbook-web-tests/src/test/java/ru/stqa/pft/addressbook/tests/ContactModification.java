package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModification extends TestBase {
  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactCreationForm(new ContactData("Denis", "Olegovich", "Sokolov", "mrbaco", "it is me, guys", "Severstal", "Cherepovets town", "89000000000", "mrbaco@ya.ru", "http://robotics-co.ru", "1", "December", "1993", "It is small note text!"));
    app.getContactHelper().submitContactModificationForm();
    app.getNavigationHelper().gotoContactPage();
  }
}
