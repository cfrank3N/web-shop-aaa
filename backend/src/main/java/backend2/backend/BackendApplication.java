package backend2.backend;

import backend2.backend.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
    private final ProductService productService;

    public BackendApplication(ProductService productService) {
        this.productService = productService;
        productService.fetchAndSaveAllProducts();
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
