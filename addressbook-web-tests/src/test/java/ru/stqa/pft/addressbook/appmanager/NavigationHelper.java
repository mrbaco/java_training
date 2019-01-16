package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {
  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void groupsPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    }

    wd.findElement(By.linkText("groups")).click();
  }

  // переход на домашнюю страницу = переход на страницу контактов
  public void contactsPage() {
    //if (isElementPresent(By.id("maintable"))) {
    //  return;
    //}

    wd.findElement(By.linkText("home")).click();
  }
}
