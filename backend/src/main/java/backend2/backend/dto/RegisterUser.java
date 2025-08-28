package backend2.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUser {

    @Email(message = "Enter a valid emailadress")
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, message = "password must contain at least 8 characters")
    private String password;

    @NotBlank
    private String confirmPassword;
}
