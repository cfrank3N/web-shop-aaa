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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        Optional<AppUser> userAlreadyExists = appUserRepository.findByUsername(newUser.getUsername());

        if (userAlreadyExists.isPresent()){
            return ResponseEntity.badRequest().body("User already exists");
        }

        AppUser appUser = new AppUser(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()));

        Authority user = authorityRepository.findByAuthority("USER")
                .orElseThrow(() -> new NoSuchElementException("Role USER does not exist"));
        Authority admin = authorityRepository.findByAuthority("ADMIN")
                .orElseThrow(() -> new NoSuchElementException("Role ADMIN does not exist"));
        List<Authority> userAuthorities = new ArrayList<>();

        if (newUser.getAuthorities() != null) {
            if (newUser.getAuthorities().contains("USER")) {
                userAuthorities.add(user);
            }
            if (newUser.getAuthorities().contains("ADMIN")) {
                userAuthorities.add(admin);
            }
            if (newUser.getAuthorities().isEmpty() ||
                    (!newUser.getAuthorities().contains("USER") &&
                            !newUser.getAuthorities().contains("ADMIN"))) {
                userAuthorities.add(user);
            }
        } else {
            userAuthorities.add(user);
        }

        appUser.setAuthorities(userAuthorities);
        appUserRepository.save(appUser);

        return ResponseEntity.ok().body("User created with roles: " + userAuthorities);
    }
}