package backend2.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FakeStoreAPITest {

    @Test
    void dummyTest() {
        System.out.println("testing");
    }

    record Rating(Double rate, Integer count) {
    }

    record Product(Long id, String title, Double price, String description, String category,
                   String imageURL, Rating rating) {
    }

    @Test
    void productById_HasAllAttributes() throws Exception {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://fakestoreapi.com/products/1"))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(200);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        assertThat(root.get("id")).as("id").isNotNull();
        assertThat(root.path("title").asText(null)).as("title").isNotBlank();
        assertThat(root.get("price")).as("price").isNotNull();
        assertThat(root.path("description").asText(null)).as("description").isNotBlank();
        assertThat(root.path("category").asText(null)).as("category").isNotBlank();
        assertThat(root.path("image").asText(null)).as("image").isNotBlank();

        JsonNode rating = root.get("rating");
        assertThat(rating).as("rating").isNotNull();
        assertThat(rating.get("rate")).as("rating.rate").isNotNull();
        assertThat(rating.get("count")).as("rating.count").isNotNull();
    }
}