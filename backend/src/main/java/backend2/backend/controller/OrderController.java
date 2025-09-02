package backend2.backend.controller;

import backend2.backend.entities.Order;
import backend2.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/order")
    public ResponseEntity<String> saveOrder(@RequestBody Order order){
        return service.saveOrder(order);
    }

}
