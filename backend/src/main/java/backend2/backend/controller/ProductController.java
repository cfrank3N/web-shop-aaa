package backend2.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("api/hello")
    public String testApi(){
        return "hello";
    }
}
