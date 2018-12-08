package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreationForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactCreationForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getFirstname());
    type(By.name("nickname"), contactData.getFirstname());
    type(By.name("title"), contactData.getFirstname());
    type(By.name("company"), contactData.getFirstname());
    type(By.name("address"), contactData.getFirstname());
    type(By.name("mobile"), contactData.getFirstname());
    type(By.name("email"), contactData.getFirstname());
    type(By.name("homepage"), contactData.getFirstname());
    type(By.name("address"), contactData.getFirstname());
    type(By.name("byear"), contactData.getFirstname());
    type(By.name("notes"), contactData.getFirstname());

    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());
  }
}
