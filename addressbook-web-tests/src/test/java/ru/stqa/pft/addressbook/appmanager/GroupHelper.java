package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class GroupHelper extends HelperBase {
  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void submitGroupCreationForm() {
    click(By.name("submit"));
  }

  public void fillGroupCreationForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModificationForm() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    groupCache = null;

    initGroupCreation();
    fillGroupCreationForm(group);
    submitGroupCreationForm();
    gotoGroupsPage();
  }

  public void modify(GroupData group) {
    groupCache = null;

    clickToInputByValue(group.getId());
    initGroupModification();
    fillGroupCreationForm(group);
    submitGroupModificationForm();
    gotoGroupsPage();
  }

  public void delete(GroupData group) {
    groupCache = null;

    clickToInputByValue(group.getId());
    deleteSelectedGroups();
    gotoGroupsPage();
  }

  public void gotoGroupsPage() {
    wd.findElement(By.linkText("groups")).click();
  }

  private Groups groupCache = null;

  public Groups all() {
    if (groupCache != null) return new Groups(groupCache);

    groupCache = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));

    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groupCache.add(new GroupData().withId(id).withName(element.getText()));
    }

    return new Groups(groupCache);
  }

  public String createRandomGroup() {
    String groupName = "Random Group #" + Integer.toString(1000000 + (int) (Math.random() * 9999999));

    gotoGroupsPage();
    create(new GroupData().withName(groupName).withHeader("auto group head").withFooter("auto group foot"));

    return groupName;
  }
}
