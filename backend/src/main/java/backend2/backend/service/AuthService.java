package backend2.backend.service;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.entities.AppUser;
import backend2.backend.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AppUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateUserCredentials(AppUserDTO appUser) {
        Optional<AppUser> optionalCustomer = repo.findByUsername(appUser.getUsername());

        if (optionalCustomer.isEmpty()) {
            return false;
        }

        AppUser customer = optionalCustomer.get();

        return passwordEncoder.matches(appUser.getPassword(), customer.getPassword());
    }

    public UserDetails getAppUserByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

}
