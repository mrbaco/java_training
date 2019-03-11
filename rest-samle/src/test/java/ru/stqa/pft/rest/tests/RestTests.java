package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.models.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

  public RestTests() throws IOException {
  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();

    Issue newIssue = new Issue().withSubject("Cherepovets test issue").
                                 withDescription("Test description");
    int issueID = createIssue(newIssue);

    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueID));

    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testStateChange() throws IOException {
    closeIssue(917);
  }
}
