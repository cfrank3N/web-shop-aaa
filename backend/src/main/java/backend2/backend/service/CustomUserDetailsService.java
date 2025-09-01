package backend2.backend.service;

import backend2.backend.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository repo;

    public CustomUserDetailsService(CustomerRepository repo) {
        this.repo = repo;
    }

    //TODO: Välja hur jag ska få min user till en UserDetails. Förmodligen lägga in lite properties i Customer
    // och sen lägga in dem I DTO'n och mappa över och låta DTO'n implementera UserDetails. Hoppas det går.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
