package backend2.backend.controller;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {

    private final AppUserService appUserService;

    public RegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> handleRegistration(@RequestBody AppUserDTO appUser) {
        return appUserService.registerCustomer(appUser);
    }

}
