package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreation extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().openContactCreationForm();
    app.getContactHelper().fillContactCreationForm(new ContactData("Denis", "Olegovich", "Sokolov", "mrbaco", "it is me, guys", "Severstal", "Cherepovets town", "89000000000", "mrbaco@ya.ru", "http://robotics-co.ru", "1", "December", "1993", "It is small note text!"));
    app.getContactHelper().submitContactCreationForm();
  }
}
