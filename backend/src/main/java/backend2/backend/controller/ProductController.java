package backend2.backend.controller;

import backend2.backend.entities.Product;
import backend2.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("api/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return service.getAllProducts();
    }
}
