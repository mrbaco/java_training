package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import ru.stqa.pft.rest.models.Issue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class TestBase {
  private final Properties properties;

  public TestBase() throws IOException {
    properties = new Properties();

    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  private boolean isIssueOpen(int issueID) throws IOException {
    Set<Issue> issues = getIssues(issueID);

    if (!issues.iterator().next().getState().equals("Resolved")) return true;
    return false;
  }

  public void skipIfNotFixed(int issueID) throws IOException {
    if (isIssueOpen(issueID)) {
      throw new SkipException("Ignored because of issue " + issueID);
    }
  }

  public Set<Issue> getIssues(int issueID) throws IOException {
    String json = getExecutor().execute(Request.Get(getProperty("bugify.URL") + "issues/" + issueID + ".json")).
            returnContent().asString();

    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  public Set<Issue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get(getProperty("bugify.URL") + "issues.json")).
            returnContent().asString();

    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  public Executor getExecutor() {
    return Executor.newInstance().auth(getProperty("bugify.API_key"), "");
  }

  public int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post(getProperty("bugify.URL") + "issues.json").
            bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription()))).
            returnContent().asString();

    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public void closeIssue(int issueID) throws IOException {
    getExecutor().execute(Request.Post(getProperty("bugify.URL") + "issues/" + issueID + ".json").
            bodyForm(new BasicNameValuePair("issue%5Bstate%5D", "3"),
                    new BasicNameValuePair("method", "update"))).
            returnContent().asString();
  }
}