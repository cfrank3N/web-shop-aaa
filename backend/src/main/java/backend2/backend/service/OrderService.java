package backend2.backend.service;

import backend2.backend.dtos.OrderDTO;
import backend2.backend.entities.Order;
import backend2.backend.repository.AppUserRepository;
import backend2.backend.repository.OrdersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrdersRepository repo;
    private final AppUserRepository userRepository; //go through service instead?

    public OrderService(OrdersRepository repo, AppUserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> saveOrder(OrderDTO dto){
        try {
            System.out.println(dto);
            Order order = new Order();
            order.setAppUser(userRepository.findById(dto.getAppUserId()).orElseThrow());
            order.setOrderedProductIds(dto.getOrderedProductIds());
            System.out.println(order);
            repo.save(order);
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

    public ResponseEntity<Order> getOrderById(Integer id){
        return ResponseEntity.ok().body(repo.findById(id).orElseThrow());
    }
}
