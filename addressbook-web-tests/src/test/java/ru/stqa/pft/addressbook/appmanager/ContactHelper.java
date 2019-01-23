package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail1());
    type(By.name("homepage"), contactData.getHomepage());
    type(By.name("address"), contactData.getAddress());
    type(By.name("byear"), contactData.getByear());
    type(By.name("notes"), contactData.getNotes());

    attach(By.name("photo"), contactData.getPhoto());

    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void contactsPage() {
    wd.findElement(By.linkText("home")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void approveDeletion() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void submitContactModificationForm() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void create(ContactData contact, boolean creation) {
    contactCache = null;

    initContactCreation();
    fillContactForm(contact, creation);
    submitContactCreationForm();
    contactsPage();
  }

  public void modify(ContactData contact) {
    contactCache = null;

    initContactModification(contact.getId());
    fillContactForm(contact, false);
    submitContactModificationForm();
    contactsPage();
  }

  public void delete(ContactData contact) {
    contactCache = null;

    clickToInputByValue(contact.getId());
    deleteSelectedContacts();
    approveDeletion();
    contactsPage();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) return new Contacts(contactCache);

    contactCache = new Contacts();

    WebElement table = wd.findElement(By.id("maintable"));
    List<WebElement> elements = table.findElements(By.name("entry"));

    if (elements.size() > 0) {
      for (WebElement element : elements) {
        List<WebElement> tds = element.findElements(By.tagName("td"));

        int id = Integer.parseInt(tds.get(0).findElement(By.tagName("input")).getAttribute("value"));
        String lastName = tds.get(1).getText();
        String firstName = tds.get(2).getText();
        String address = tds.get(3).getText();
        String allEmails = tds.get(4).getText();
        String homepage = tds.get(9).getText();

        String allPhones = tds.get(5).getText();

        ContactData contact = new ContactData().withId(id).
                                                withFirstname(firstName).
                                                withLastname(lastName).
                                                withAddress(address).
                                                withAllPhones(allPhones).
                                                withAllEmails(allEmails).
                                                withHomepage(homepage);
        contactCache.add(contact);
      }
    }

    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact.getId());

    String middleName = wd.findElement(By.name("middlename")).getAttribute("value");
    String nickname = wd.findElement(By.name("nickname")).getAttribute("value");
    String title = wd.findElement(By.name("title")).getAttribute("value");
    String company = wd.findElement(By.name("company")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String bday = wd.findElement(By.name("bday")).getAttribute("value");
    String bmonth = wd.findElement(By.name("bmonth")).getAttribute("value");
    String byear = wd.findElement(By.name("byear")).getAttribute("value");
    String notes = wd.findElement(By.name("notes")).getAttribute("value");

    wd.navigate().back();

    return new ContactData().withId(contact.getId()).
                             withFirstname(contact.getFirstname()).
                             withMiddlename(middleName).
                             withLastname(contact.getLastname()).
                             withNickname(nickname).
                             withTitle(title).
                             withCompany(company).
                             withAddress(contact.getAddress()).
                             withHomePhone(homePhone).
                             withMobilePhone(mobilePhone).
                             withWorkPhone(workPhone).
                             withEmail1(email1).
                             withEmail2(email2).
                             withEmail3(email3).
                             withHomepage(contact.getHomepage()).
                             withBday(bday).
                             withBmonth(bmonth).
                             withByear(byear).
                             withNotes(notes);
  }
}
