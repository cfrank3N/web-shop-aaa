package backend2.backend.service;

import backend2.backend.dtos.OrderDTO;
import backend2.backend.entities.AppUser;
import backend2.backend.entities.Order;
import backend2.backend.repository.OrdersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrdersRepository repo;

    public OrderService(OrdersRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<String> saveOrder(OrderDTO dto){
        try {
            Order order = new Order();
            order.setAppUser((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            order.setProductIdAndQty(dto.getProductIdAndQty());

            repo.save(order);
            System.out.println("Order saved: " + order);
            return ResponseEntity.ok("Order placed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to place order " + e.getMessage());
        }
    }
    // Is exception handling needed?
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok().body(repo.findAll());
    }

    public ResponseEntity<Order> getOrderById(Long id){
        return ResponseEntity.ok().body(repo.findById(id).orElseThrow());
    }

    public ResponseEntity<String> deleteOrderById(Long id) {
        try {
            Order order = repo.findById(id).orElseThrow();
            repo.delete(order);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found or could not be deleted: " + e.getMessage());
        }
    }
}