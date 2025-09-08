package backend2.backend.controller;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.service.AuthService;
import backend2.backend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService service;

    public AuthController(JwtUtil jwtUtil, AuthService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUserDTO appUser) {

        //validate user here from AuthService Class

        //Test sout's
        System.out.println("Authorities: " + appUser.getAuthorities());
        System.out.println("Username: " + appUser.getUsername());
        System.out.println("Password: " + appUser.getPassword());

        if (service.validateUserCredentials(appUser)) {
            return ResponseEntity.ok().body(jwtUtil.generateToken(service.getAppUserByUsername(appUser.getUsername())));
        } else {
            return ResponseEntity.badRequest().body("Invalid password or Username");
        }

    }

    @GetMapping("/restricted-view")
    public String accessRestrictedView() {
        return "You have access to this restricted view";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String accessUserView() {
        return "You are a user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> accessAdminView() {
        return ResponseEntity.ok().body("Access granted");
    }

}
