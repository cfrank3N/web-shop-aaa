package backend2.backend.controller;

import backend2.backend.dtos.OrderDTO;
import backend2.backend.entities.Order;
import backend2.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<String> saveOrder(@RequestBody OrderDTO order){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.saveOrder(order, userName);
    }

    // todo add auth @PreAuthorize()
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return service.getAllOrders();
    }

    // todo add auth @PreAuthorize()
    @GetMapping("/order")
    public ResponseEntity<Order> getOrderById(@RequestParam Integer id){
        return service.getOrderById(id);
    }
}
