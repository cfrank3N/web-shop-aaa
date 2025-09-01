package backend2.backend.controller;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.util.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final JwtUtil jwtUtil;

    public LoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody AppUserDTO appUser) {

        //validate user here from LoginService Class

        //Test sout's
        System.out.println("Authorities: " + appUser.getAuthorities());
        System.out.println("Username: " + appUser.getUsername());
        System.out.println("Password: " + appUser.getPassword());

        return jwtUtil.generateToken(appUser.getUsername());
    }

    @GetMapping("/restricted-view")
    public String accessRestrictedView() {
        return "You have access to this restricted view";
    }

}
