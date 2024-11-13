package com.ef.interview.util;

import com.ef.interview.model.user.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ThirdPartyHelper {

    public UserDTO prepareUser() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://randomuser.me/api/")).GET().build();

        HttpResponse<String> response;

        {
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode userNode = rootNode.path("results").get(0);
                String name = userNode.path("name").path("first").asText() + " " +
                        userNode.path("name").path("last").asText();
                String address = userNode.path("location").path("city").asText();
                String email = userNode.path("email").asText();
                UserDTO userDetails = new UserDTO(name, address, email);
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Response Body: " + userDetails);
                return userDetails;



            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
