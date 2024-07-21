package java2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UpdateUser2{
    public void  updateUser(int id) {
        try {

            JsonObject user = new JsonObject();
            user.addProperty("id", id);
            user.addProperty("name", "John Deer Update");
            user.addProperty("username", "johndeer2");
            user.addProperty("email", "johndeer2@example.com");

            JsonObject address = new JsonObject();
            address.addProperty("street", "Khreshchatyk St");
            address.addProperty("suite", "Apt. 1");
            address.addProperty("city", "Kiev");
            address.addProperty("zipcode", "01001—04116");

            JsonObject geo = new JsonObject();
            geo.addProperty("lat", "50°27′08″");
            geo.addProperty("lng", "30°31′38″");
            address.add("geo", geo);

            user.add("address", address);
            user.addProperty("phone", "+380  44 125 03 03 x56442");
            user.addProperty("website", "johndeer.com");

            JsonObject company = new JsonObject();
            company.addProperty("name", "John's Company");
            company.addProperty("catchPhrase", "Innovate and Build");
            company.addProperty("bs", "synergize scalable solutions");

            user.add("company", company);

            Gson gson = new Gson();
            String json = gson.toJson(user);


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://jsonplaceholder.typicode.com/users/"+id))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(json))
                    .build();



            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());


            System.out.println(response.body());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
