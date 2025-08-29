package backend2.backend.controller;

import backend2.backend.dtos.CustomerDTO;
import backend2.backend.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        System.out.println("=== CustomerController CREATED ===");
        this.service = service;
    }

    @GetMapping("/api/test")
    public CustomerDTO testCustomer() {
        System.out.println("Endpoint /test used");
        return service.printAllCustomersAuthorities();
    }

    @GetMapping("/api/test2")
    public List<String> testAuthority() {
        System.out.println("Endpoint /test used");
        return service.printAllCustomersAuthorities2();
    }

}
