package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactContacts extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().contactsPage();

    if (!app.contact().isThereAnySelect()) {
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
                                             withGroup("test1").
                                             withNotes("It is small note text!"), true);
    }
  }

  @Test
  public void testContactContacts() {
    app.goTo().contactsPage();

    ContactData contact = app.contact().all().iterator().next();
    ContactData fullContact = app.contact().infoFromEditForm(contact);

    String[] mergedData = mergeContacts(fullContact);

    assertThat(contact.getAllPhones(), equalTo(mergedData[0]));
    assertThat(contact.getAllEmails(), equalTo(mergedData[1]));
    assertThat(contact.getAddress(), equalTo(fullContact.getAddress()));
  }

  public String[] mergeContacts(ContactData contact) {
    String[] c = {
            Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                    .stream().map(this::cleaned).filter((s) -> !s.equals("")).collect(Collectors.joining("\n")),
            Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
                    .stream().filter((s) -> !s.equals("")).collect(Collectors.joining("\n"))
    };

    return c;
  }

  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
