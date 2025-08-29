package backend2.backend.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomerDTO {
    private int id;
    private String username;
    private String password;
    private List<String> authorities;
}
