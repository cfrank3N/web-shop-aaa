package backend2.backend.service;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.entities.AppUser;
import backend2.backend.entities.Authority;
import backend2.backend.repository.AppUserRepository;
import backend2.backend.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public AppUserService(AppUserRepository appUserRepository,
                          PasswordEncoder passwordEncoder,
                          AuthorityRepository authorityRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public ResponseEntity<String> registerCustomer(AppUserDTO newUser) {
        appUserRepository.findByUsername(newUser.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already in use");
        });

        AppUser appUser = new AppUser(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()));


        Optional<Authority> auth = authorityRepository.findByAuthority("USER");
        if (auth.isEmpty()) {
            Authority authority = new Authority();
            authority.setAuthority("USER");
            authorityRepository.save(authority);
            appUser.setAuthorities(List.of(authority));
            appUserRepository.save(appUser);

        } else {
            appUser.setAuthorities(List.of(auth.get()));
            appUserRepository.save(appUser);
        }

        return ResponseEntity.ok().body("User created!");
    }
}