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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;




public class GetOpenTasks {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();
    public static void getOpenTasks(int userId) throws Exception {

    String todosUrl = "https://jsonplaceholder.typicode.com/users/" + userId + "/todos";
    HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(todosUrl))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JsonArray todos = gson.fromJson(response.body(), JsonArray.class);


    List<JsonObject> openTasks = Arrays.stream(gson.fromJson(todos, JsonObject[].class))
            .filter(task -> !task.get("completed").getAsBoolean())
            .collect(Collectors.toList());


    if (openTasks.isEmpty()) {
        System.out.println("У користувача немає відкритих задач.");
    } else {
        System.out.println("Відкриті задачі користувача з ID " + userId + ":");
        openTasks.forEach(task -> System.out.println(task));
    }
}

    public static class UserPostsComments {
        private static final HttpClient client = HttpClient.newHttpClient();
        private static final Gson gson = new Gson();
    
    
    
        public static void getLastPostComments(int userId) throws Exception {
    
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
}
