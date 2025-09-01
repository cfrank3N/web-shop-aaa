package backend2.backend.service;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.repository.CustomerRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomerRepository repo;

    public LoginService(CustomerRepository repo) {
        this.repo = repo;
    }

    public boolean validateUserCredentials(AppUserDTO appUser) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }

}
