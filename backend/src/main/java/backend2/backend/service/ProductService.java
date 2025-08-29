package backend2.backend.service;

import backend2.backend.entities.Product;
import backend2.backend.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(repo.findAll());
    }

    public void deleteAll(){
        repo.deleteAll();
    }

    public void saveAll(List<Product> products){
        repo.saveAll(products);
    }
}
