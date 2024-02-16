package org.example.panda.aplicationSecurity.services.models.dtos;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotNull(message = "El usuario no puede ser nulo")
    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
    private String username;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "La contraseña debe contener al menos una mayúscula, un carácter especial y tener un mínimo de 8 dígitos."
    )
    @NotNull(message = "La contraseña no puede ser nula")
    private String password;
}
