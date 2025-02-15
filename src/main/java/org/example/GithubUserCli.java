package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GithubUserCli {

    public static void main(String[] args) {

        GithubUserCli cli = new GithubUserCli();

        if (args.length != 1) {
            cli.printUsage();
            return;
        }

        cli.fetchGithubUserActivity(args[0]);
    }

    private void fetchGithubUserActivity(String username) {
        String GITHUB_API_URL = "https://api.github.com/users/" + username + "/events";

        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(GITHUB_API_URL))
                    .header("Accept", "application/vnd.github+json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                System.out.println("User not found. Please make sure you entered correct username");
                return;
            }

            if (response.statusCode() == 200) {
                JsonArray jsonElements = JsonParser.parseString(response.body()).getAsJsonArray();
                displayActivities(jsonElements);
            } else {
                System.out.println("Response code: " + response.statusCode());
            }

        } catch (URISyntaxException | IOException | InterruptedException uriSyntaxException) {
            System.out.println(uriSyntaxException.getMessage());
        }
    }

    private void displayActivities(JsonArray events) {
        for (JsonElement element: events) {
            JsonObject event = element.getAsJsonObject();
            String type = event.get("type").getAsString();
            String action = switch (type) {
                case "PushEvent" ->
                     "Pushed " + event.get("payload").getAsJsonObject().get("commits").getAsJsonArray().size()
                             + " commit(s) to " + event.get("repo").getAsJsonObject().get("name");
                case "IssuesEvent" ->
                        event.get("payload").getAsJsonObject().get("action").getAsString().toUpperCase().charAt(0)
                                + event.get("payload").getAsJsonObject().get("action").getAsString()
                                + " an issue in ${event.repo.name}";
                case "WatchEvent" -> "Starred " + event.get("repo").getAsJsonObject().get("name").getAsString();
                case "ForkEvent" -> "Forked " + event.get("repo").getAsJsonObject().get("name").getAsString();
                case "CreateEvent" -> "Created " + event.get("payload").getAsJsonObject().get("ref_type").getAsString()
                        + " in " + event.get("repo").getAsJsonObject().get("name").getAsString();
                default -> event.get("type").getAsString().replace("Event", "")
                        + " in " + event.get("repo").getAsJsonObject().get("name").getAsString();
            };
            System.out.println(action);
        }
    }

    private void printUsage() {
        System.out.println("""
                Command: java GithubActivity <username>
                Example: java GithubActivity stohirov\s
               \s""");
    }

}