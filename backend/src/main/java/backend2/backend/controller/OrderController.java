package backend2.backend.controller;

import backend2.backend.entities.Order;
import backend2.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }
    // todo make sure sent from same user
    @PostMapping("/order")
    public ResponseEntity<String> saveOrder(@RequestBody Order order){
        return service.saveOrder(order);
    }

    // todo add auth @PreAuthorize()
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return service.getAllOrders();
    }
//
//    // todo add auth @PreAuthorize()
//    @GetMapping("/orders/${id}")
//    public ResponseEntity<Order> getOrderById(@RequestParam Integer id){
//        return service.getOrderById(id);
//    }
}
