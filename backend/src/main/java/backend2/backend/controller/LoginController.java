package backend2.backend.controller;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return jwtUtil.generateToken(appUser.getUsername());
    }

}
