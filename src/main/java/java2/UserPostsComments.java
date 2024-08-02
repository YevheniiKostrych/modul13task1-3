package java2;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserPostsComments {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();


    public void getLastPostComments(int userId) throws Exception {

        String postsUrl = "https://jsonplaceholder.typicode.com/users/" + userId + "/posts";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(postsUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray posts = gson.fromJson(response.body(), JsonArray.class);

        if (posts.size() == 0) {
            System.out.println("Користувач не має постів.");
            return;
        }


        JsonObject lastPost = posts.get(posts.size() - 1).getAsJsonObject();
        int postId = lastPost.get("id").getAsInt();


        String commentsUrl = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
        request = HttpRequest.newBuilder()
                .uri(new URI(commentsUrl))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray comments = gson.fromJson(response.body(), JsonArray.class);


        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(comments, fileWriter);
            System.out.println("Коментарі збережені у файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
