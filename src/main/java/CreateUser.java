
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HelloWorld {
    public static void main(String[] args) {


    JsonObject user = new JsonObject();
        user.addProperty("name", "John Doe");
        user.addProperty("username", "johndoe");
        user.addProperty("email", "johndoe@example.com");

    JsonObject address = new JsonObject();
        address.addProperty("street", "Main St");
        address.addProperty("suite", "Apt. 1");
        address.addProperty("city", "New York");
        address.addProperty("zipcode", "10001");

    JsonObject geo = new JsonObject();
        geo.addProperty("lat", "40.7128");
        geo.addProperty("lng", "-74.0060");
        address.add("geo", geo);

        user.add("address", address);
        user.addProperty("phone", "1-770-736-8031 x56442");
        user.addProperty("website", "johndoe.com");

    JsonObject company = new JsonObject();
        company.addProperty("name", "John's Company");
        company.addProperty("catchPhrase", "Innovate and Lead");
        company.addProperty("bs", "synergize scalable solutions");

        user.add("company", company);

    Gson gson = new Gson();
    String json = gson.toJson(user);
     try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://jsonplaceholder.typicode.com/users"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());


        System.out.println(response.body());
    } catch (Exception e) {
        e.printStackTrace();
    }
}  }


