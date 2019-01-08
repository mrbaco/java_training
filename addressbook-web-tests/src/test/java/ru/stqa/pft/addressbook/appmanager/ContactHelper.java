package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreationForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
    type(By.name("homepage"), contactData.getHomepage());
    type(By.name("address"), contactData.getAddress());
    type(By.name("byear"), contactData.getByear());
    type(By.name("notes"), contactData.getNotes());

    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void approveDeletion() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int index) {
    String id = wd.findElements(By.name("selected[]")).get(index).getAttribute("value");
    wd.get("http://localhost/edit.php?id=" + id);
  }

  public void submitContactModificationForm() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void createContact(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, creation);
    submitContactCreationForm();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<>();

    WebElement table = wd.findElement(By.id("maintable"));
    List<WebElement> elements = table.findElements(By.name("entry"));

    if (elements.size() > 0) {
      for (WebElement element : elements) {
        List<WebElement> tds = element.findElements(By.tagName("td"));

        int id = Integer.parseInt(tds.get(0).findElement(By.tagName("input")).getAttribute("value"));
        String lastName = tds.get(1).getText();
        String firstName = tds.get(2).getText();
        String address = tds.get(3).getText();
        String email = tds.get(4).getText();
        String mobile = tds.get(5).getText();
        String homepage = tds.get(9).getText();

        ContactData contact = new ContactData(id, firstName, null, lastName, null, null, null, address, mobile, email, homepage, null, null, null, null, null);
        contacts.add(contact);
      }
    }

    return contacts;
  }
}
