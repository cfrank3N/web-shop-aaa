package backend2.backend;

import backend2.backend.client.FakeStoreClient;
import backend2.backend.entities.Product;
import backend2.backend.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

@SpringBootTest
@ActiveProfiles("test")
public class FakeStorePersistedDbParityTest {

    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Autowired
    private ProductRepository productRepository;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Test
    @DisplayName("API product attributes should match persisted DB product attributes")
    void apiProductToMatchPersistedProduct() throws Exception {
        long productId = 1L;

        fakeStoreClient.fetchAndSaveAllProducts();

        ApiProduct apiProduct = fetchApiProduct(productId);
        Product dbProduct = productRepository.findById((int) productId)
                .orElseThrow(() -> new AssertionError("Product " + productId + "not found in db"));


        assertThat(dbProduct.getId()).isEqualTo(apiProduct.id().intValue());
        assertThat(dbProduct.getTitle()).isEqualTo(apiProduct.title());
        assertThat(dbProduct.getPrice()).isCloseTo(apiProduct.price(), within(1e-6));
        assertThat(dbProduct.getDescription()).isEqualTo(apiProduct.description());
        assertThat(dbProduct.getCategory()).isEqualTo(apiProduct.category());
        assertThat(dbProduct.getImage()).isEqualTo(apiProduct.image());

        assertThat(dbProduct.getRating()).isNotNull();
        assertThat(dbProduct.getRating().getRate())
                .isCloseTo(apiProduct.rating().rate(), within(1e-6));
        assertThat(dbProduct.getRating().getCount())
                .isEqualTo(apiProduct.rating().count());
    }

    private ApiProduct fetchApiProduct(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://fakestoreapi.com/products/" + id))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        assertThat(response.statusCode()).isEqualTo(200);
        return mapper.readValue(response.body(), ApiProduct.class);

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record ApiProduct(Long id, String title, Double price, String description, String category, String image,
                              ApiRating rating) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record ApiRating(Double rate, Integer count) {
    }
}