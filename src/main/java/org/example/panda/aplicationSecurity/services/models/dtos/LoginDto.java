package org.example.panda.aplicationSecurity.services.models.dtos;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @Email(message = "El formato del correo electrónico no es válido.")
    @Column(name = "email", length = 255)
    @NotNull(message = "El email no puede ser nulo")
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "La contraseña debe contener al menos una mayúscula, un carácter especial y tener un mínimo de 8 dígitos."
    )
    @NotNull(message = "La contraseña no puede ser nula")
    private String password;
}
