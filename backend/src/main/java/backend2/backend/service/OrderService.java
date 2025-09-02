package backend2.backend.service;

import backend2.backend.entities.Order;
import backend2.backend.repository.OrdersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrdersRepository repo;

    public OrderService(OrdersRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<String> saveOrder(Order order){
        try {
            repo.save(order);
            return ResponseEntity.ok("Order placed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to place order " + e.getMessage());
        }
    }
}
