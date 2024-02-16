package org.example.panda.aplicationSecurity.services.models.dtos;


import jakarta.validation.constraints.*;
import lombok.*;
import org.example.panda.aplicationSecurity.persistence.entities.Role;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Long id;

    @Size(min = 6, message = "El documento de identidad tiene que tener más de 6 caracteres.")
    @NotNull(message = "Este campo no puede ser nulo")
    private String numIdentidad;

    @NotEmpty(message = "El nombre no puede estar vacio o nulo.")
    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 50 caracteres.")
    private String nombres;

    @NotEmpty(message = "El apellido no puede estar vacio o nulo.")
    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")

    private String apellidos;

    @Email(message = "El formato del correo electrónico no es válido.")
    @NotNull(message = "El email no puede ser nulo")
    private String email;

    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
    private String username;

    @Pattern(regexp = "\\d{8,15}", message = "El número de teléfono debe tener entre 8 y 15 dígitos.")
    private String telefono;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "La contraseña debe contener al menos una mayúscula, un carácter especial y tener un mínimo de 8 dígitos."
    )
    @NotNull(message = "Este campo no puede ser nulo")
    private String password;

    private Set<Role> roles; //para que el rol sea único
}
