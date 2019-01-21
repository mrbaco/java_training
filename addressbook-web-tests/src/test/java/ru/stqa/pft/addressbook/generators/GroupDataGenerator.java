package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Groups count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();
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
    List<GroupData> groups = generateGroups(count);

    if (format.equals("csv")) {
      save(groups, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(groups, new File(file));
    } else {
      System.out.println(String.format("Unrecognized format: %s", format));
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    String json = gson.toJson(groups);

    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void save(List<GroupData> groups, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      for (GroupData g : groups) {
        writer.write(String.format("%s;%s;%s\n", g.getName(), g.getHeader(), g.getFooter()));
      }
    }
  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("test group %s", i)).
              withHeader(String.format("header %s", i)).
              withFooter(String.format("footer %s", i)));
    }

    return groups;
  }
}
