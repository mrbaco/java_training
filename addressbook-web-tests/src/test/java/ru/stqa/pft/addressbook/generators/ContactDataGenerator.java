package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = "-c", description = "Contacts count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);

    if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println(String.format("Unrecognized format: %s", format));
    }
  }

  public void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    String json = gson.toJson(contacts);

    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();

    String[] firstNames = {"Pavel", "Denis", "Maxim", "Alexander"};
    String[] lastNames = {"Konovalov", "Sokolov", "Abakumov", "Zabaluev"};
    String[] middleNames = {"Sergeevich", "Olegovich", "Sergeevich", "Nikolaevich"};
    String[] nicknames = {"fra", "mrbaco", "max.abakumov", "Alexander03"};
    String[] addresses = {"Cherepovets, Tonshalovo", "Cherepovets, Montkler", "Saint Petersburg, Lesnaya", "Cherepovets, Krasnaya"};
    String[] phones = {"111-11-11-11", "222-22-22-22", "333-33-33-33", "444-44-44-44"};
    String[] emails = {"fra000@pochta.ru", "mrbaco@ya.ru", "max.abakumov1995@yandex.ru", "alex.zabaluev@ya.ru"};
    String[] photos = {"src/test/resources/1.jpg", "src/test/resources/2.jpg", "src/test/resources/3.jpg", "src/test/resources/4.jpg"};

    Integer[] randoms = new Integer[8];

    for (int i = 0; i < count; i++) {
      randoms[0] = (int) (Math.random() * 3);
      randoms[1] = (int) (Math.random() * 3);
      randoms[2] = (int) (Math.random() * 3);
      randoms[3] = (int) (Math.random() * 3);
      randoms[4] = (int) (Math.random() * 3);
      randoms[5] = (int) (Math.random() * 3);
      randoms[6] = (int) (Math.random() * 3);
      randoms[7] = (int) (Math.random() * 3);

      contacts.add(new ContactData().withFirstname(firstNames[randoms[0]]).
                                     withLastname(lastNames[randoms[1]]).
                                     withMiddlename(middleNames[randoms[2]]).
                                     withNickname(nicknames[randoms[3]]).
                                     withAddress(addresses[randoms[4]]).
                                     withMobilePhone(phones[randoms[5]]).
                                     withEmail1(emails[randoms[6]]).
                                     withPhotoAsString(photos[randoms[7]]).
                                     withTitle("it is me, guys").
                                     withCompany("Severstal").
                                     withHomepage("http://robotics-co.ru").
                                     withBday("1").
                                     withBmonth("December").
                                     withByear("1993").
                                     withGroup("test1").
                                     withNotes("It is small note text!"));
    }

    return contacts;
  }
}
