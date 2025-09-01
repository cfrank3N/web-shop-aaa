package backend2.backend.mappers;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.entities.Authority;
import backend2.backend.entities.Customer;
import org.springframework.security.core.GrantedAuthority;

public class CustomerMapper {
    public static AppUserDTO customerToCustomerDTODetailed(Customer c) {
        return AppUserDTO.builder()
                .id(c.getId())
                .username(c.getUsername())
                .password(c.getPassword())
                .authorities(c.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();
    }
}
