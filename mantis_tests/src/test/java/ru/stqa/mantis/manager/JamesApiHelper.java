package ru.stqa.mantis.manager;

import okhttp3.*;

import java.io.IOException;
import java.net.CookieManager;

public class JamesApiHelper extends HelperBase {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;

    public JamesApiHelper(ApplicationManager manager) {
        super(manager);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();
    }

    public void addUser(String email, String password) {

        String url = manager.property("james.apiBaseUrl") + "/users/" + email;

        RequestBody body = RequestBody.create(
                String.format("{\"password\":\"%s\"}", password),
                JSON
        );

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to add user via James API: " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


