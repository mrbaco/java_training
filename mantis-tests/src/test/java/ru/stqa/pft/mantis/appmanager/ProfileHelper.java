package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.List;

public class ProfileHelper extends HelperBase {
  public ProfileHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseURL") + "/signup_page.php");

    type(By.name("username"), username);
    type(By.name("email"), email);

    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);

    type(By.name("password"), password);
    type(By.name("password_confirm"), password);

    click(By.cssSelector("button[type='submit']"));
  }

  public void login(String username, String password) {
    type(By.name("username"), username);
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[type='submit']"));
  }

  public void logout() {
    wd.get(app.getProperty("web.baseURL") + "/logout_page.php");
  }

  public void selectUser(String username) {
    wd.get(app.getProperty("web.baseURL") + "/manage_user_page.php");
    click(By.linkText(username));
  }

  public void resetPassword() {
    click(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email, boolean last) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).reduce((first, second) -> second)
            .orElse(null);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

    return regex.getText(mailMessage.text);
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

    return regex.getText(mailMessage.text);
  }
}
