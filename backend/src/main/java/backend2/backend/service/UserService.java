package backend2.backend.service;

import backend2.backend.entities.Role;
import backend2.backend.entities.User;
import backend2.backend.repository.RoleRepository;
import backend2.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerCustomer(String username, String rawPassword) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new IllegalArgumentException("Username already in use");
        });

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name("CUSTOMER").build()
                ));

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .enabled(true)
                .build();

        user.getRoles().add(customerRole);

        return userRepository.save(user);
    }
}