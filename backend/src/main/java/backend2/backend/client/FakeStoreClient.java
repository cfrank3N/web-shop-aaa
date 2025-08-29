package backend2.backend.client;

import backend2.backend.entities.Product;
import backend2.backend.service.ProductService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/*  To make sure products are up to date we remove and add all products
    This is done on startup and at 03:00 every day */

@Component
public class FakeStoreClient {
    private final ProductService service;

    public FakeStoreClient(ProductService service) {
        this.service = service;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 3 * * *")
    public void fetchAndSaveAllProducts() {
        try {
            WebClient client = WebClient.create("https://fakestoreapi.com/products");
            List<Product> products = client.get().retrieve().bodyToFlux(Product.class).collectList().block();

            if (products != null) {
                service.deleteAll();
                service.saveAll(products);
                System.out.println("Products updated");
            } else {
                System.err.println("No products available");
            }
        } catch (RuntimeException e) {
            System.err.println("Error connecting to API: " + e.getMessage());
        }
    }
}
