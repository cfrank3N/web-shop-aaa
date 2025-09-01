package backend2.backend.controller;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.service.LoginService;
import backend2.backend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final JwtUtil jwtUtil;
    private final LoginService service;

    public LoginController(JwtUtil jwtUtil, LoginService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUserDTO appUser) {

        //validate user here from LoginService Class

        //Test sout's
        System.out.println("Authorities: " + appUser.getAuthorities());
        System.out.println("Username: " + appUser.getUsername());
        System.out.println("Password: " + appUser.getPassword());

        if (service.validateUserCredentials(appUser)) {
            return ResponseEntity.ok().body(jwtUtil.generateToken(appUser.getUsername()));
        } else {
            return ResponseEntity.badRequest().body("Invalid password or Username");
        }

    }

    @GetMapping("/restricted-view")
    public String accessRestrictedView() {
        return "You have access to this restricted view";
    }

}
