package ru.stqa.mantis.manager;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.IssuesApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.Identifier;
import io.swagger.client.model.Issue;
import okhttp3.*;
import ru.stqa.mantis.model.IssueData;

import java.io.IOException;
import java.net.CookieManager;

public class RestApiHelper extends HelperBase {

    private OkHttpClient client;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public RestApiHelper(ApplicationManager manager) {
        super(manager);
        client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager())).build();
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth Authorization = (ApiKeyAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setApiKey(manager.property("apiKey"));
    }

    public void createIssue(IssueData issueData) {

        Issue issue = new Issue();
        issue.setSummary(issueData.summary());
        issue.setDescription(issueData.description());
        var projectId = new Identifier();
        projectId.setId(issueData.project());
        issue.setProject(projectId);
        var categoryId = new Identifier();
        categoryId.setId(issueData.category());
        issue.setCategory(categoryId);

        IssuesApi apiInstance = new IssuesApi();
        try {
            apiInstance.issueAdd(issue);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRegistration(String username, String email) {
        String url = manager.property("web.baseUrl") + "/api/rest/users";

        RequestBody body = RequestBody.create(
                String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email),
                JSON
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", manager.property("apiKey"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to start registration via Mantis REST API: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


