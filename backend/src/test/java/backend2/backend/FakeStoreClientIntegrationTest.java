package backend2.backend;

import backend2.backend.client.FakeStoreClient;
import backend2.backend.entities.Product;
import backend2.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class FakeStoreClientIntegrationTest {

    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void fetchAndSaveAllProducts() {
        fakeStoreClient.fetchAndSaveAllProducts();

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).isNotEmpty();

        Product firstProduct = allProducts.get(0);
        assertThat(firstProduct.getId()).isGreaterThan(0);
        assertThat(firstProduct.getTitle()).isNotBlank();
        assertThat(firstProduct.getPrice()).isGreaterThan(0);
        assertThat(firstProduct.getDescription()).isNotBlank();
        assertThat(firstProduct.getImage()).isNotBlank();
        assertThat(firstProduct.getCategory()).isNotBlank();
        assertThat(firstProduct.getRating()).isNotNull();
    }
}