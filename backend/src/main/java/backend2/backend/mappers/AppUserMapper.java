package backend2.backend.mappers;

import backend2.backend.dtos.AppUserDTO;
import backend2.backend.entities.AppUser;
import org.springframework.security.core.GrantedAuthority;

public class AppUserMapper {
    public static AppUserDTO appUserToAppUserDTODetailed(AppUser c) {
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
