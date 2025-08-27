package backend2.backend.service;

import backend2.backend.entities.Product;
import backend2.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    //Todo fix error handling.
    public void fetchAndSaveAllProducts(){
        try {
            WebClient client = WebClient.create("https://fakestoreapi.com/products");
            List<Product> products = client.get().retrieve().bodyToFlux(Product.class).collectList().block();

            if (products != null) {
                repo.saveAll(products);
                //Hibernate automatically checks if product already exists in DB and only add/update if necessary
            } else {
                System.err.println("No products available");
            }
        } catch (RuntimeException e){
            System.err.println("Error connecting to API: " + e.getMessage());
        }
    }
}
