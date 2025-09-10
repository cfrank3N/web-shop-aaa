package backend2.backend.controller;

import backend2.backend.dtos.OrderDTO;
import backend2.backend.entities.Order;
import backend2.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> saveOrder(@RequestBody OrderDTO order){
        return service.saveOrder(order);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders(){
        return service.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) { return service.getOrderById(id); }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) { return service.deleteOrderById(id); }

}
