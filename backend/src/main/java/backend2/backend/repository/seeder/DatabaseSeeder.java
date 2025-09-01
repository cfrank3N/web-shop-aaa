package backend2.backend.repository.seeder;

import backend2.backend.entities.Authority;
import backend2.backend.entities.Customer;
import backend2.backend.repository.AuthorityRepository;
import backend2.backend.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder {

    private final CustomerRepository customerRepository;
    private final AuthorityRepository authorityRepository;


    public DatabaseSeeder(CustomerRepository customerRepository, AuthorityRepository authorityRepository) {
        this.customerRepository = customerRepository;
        this.authorityRepository = authorityRepository;
    }

    @PostConstruct
    public void seed() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (customerRepository.count() == 0 && authorityRepository.count() == 0) {

            Authority authority1 = new Authority();
            Authority authority2 = new Authority();
            authority1.setAuthority("USER");
            authority2.setAuthority("ADMIN");

            authorityRepository.saveAll(List.of(authority1, authority2));

            Customer c1 = new Customer("frank", passwordEncoder.encode("hej123"));
            Customer c2 = new Customer("carro", passwordEncoder.encode("abc123"));
            c1.setAuthorities(List.of(authority1, authority2));
            c2.setAuthorities(List.of(authority1));

            customerRepository.saveAll(List.of(c1, c2));

        }

    }

}
