package backend2.backend.controller;

import backend2.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import backend2.backend.dto.RegisterUser;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("form", new RegisterUser());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@Valid @ModelAttribute("form") RegisterUser form,
                                     BindingResult binding,
                                     Model model) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            binding.rejectValue("confirmPassword","mismatch","passwords do not match");
        }

        if (binding.hasErrors()) {
            return "register";
        }

        try {
            userService.registerCustomer(form.getUsername(), form.getPassword());
            model.addAttribute("success", "account created.");
            model.addAttribute("form", new RegisterUser());
            return "register";

        } catch (IllegalArgumentException ex) {
            binding.rejectValue("username","exists", ex.getMessage());
            return "register";
        }
    }
}
