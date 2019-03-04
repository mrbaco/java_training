package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTests extends TestBase {
  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testPasswordChange() throws IOException, MessagingException {
    // получаем список пользователей
    Users users = app.db().users();

    // выбор пользователя, которому будем менять пароль
    User man = users.iterator().next();

    String email = man.getEmail();
    String user = man.getUsername();

    String newPassword = "newPassword";

    // вход под администратором
    app.registration().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));

    // переход к странице редактирования пользователя
    app.registration().selectUser(user);

    // сброс пароля
    app.registration().resetPassword();

    // получение почты
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, email, true);

    // смена пароля
    app.registration().finish(confirmationLink, newPassword);

    // проверка изменений
    HttpSession session = app.newSession();

    assertTrue(session.login(user, newPassword));
    assertTrue(session.isLoggedInAs(user));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
