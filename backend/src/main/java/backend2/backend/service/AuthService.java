package backend2.backend.service;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.entities.Customer;
import backend2.backend.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final CustomerRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CustomerRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateUserCredentials(AppUserDTO appUser) {
        Optional<Customer> optionalCustomer = repo.findByUsername(appUser.getUsername());

        if (optionalCustomer.isEmpty()) {
            return false;
        }

        Customer customer = optionalCustomer.get();

        if (!passwordEncoder.matches(appUser.getPassword(), customer.getPassword())) {
            return false;
        }

        return true;
    }

    public UserDetails getCustomerByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

}
