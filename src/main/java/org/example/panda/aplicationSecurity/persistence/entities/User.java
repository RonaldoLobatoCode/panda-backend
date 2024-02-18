package org.example.panda.aplicationSecurity.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "doc_identidad" }),
                @UniqueConstraint(columnNames = { "email" }), @UniqueConstraint(columnNames = { "username" }) })
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @Size(min = 6, message = "El documento de identidad tiene que tener más de 6 caracteres.")
        @Column(name = "doc_identidad", unique = true, nullable = false, length = 20)
        @NotNull(message = "Este campo no puede ser nulo")
        private String numIdentidad;

        @NotEmpty(message = "El nombre no puede estar vacio o nulo.")
        @Size(min = 2, message = "El nombre tiene que tener como mínimo 2 caracteres.")
        @Size(max = 50, message = "Este campo solo puede tener un máximo de 50 caracteres.")
        @Column(name = "nombres", nullable = false, length = 50)
        private String nombres;

        @NotEmpty(message = "El apellido no puede estar vacio o nulo.")
        @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
        @Column(name = "apellidos", nullable = false, length = 50)
        private String apellidos;

        @Email(message = "El formato del correo electrónico no es válido.")
        @Column(name = "email", length = 255)
        @NotNull(message = "El email no puede ser nulo")
        private String email;

        @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
        @Column(name = "username", nullable = false, length = 50)
        private String username;

        @Pattern(regexp = "\\d{8,15}", message = "El número de teléfono debe tener entre 8 y 15 dígitos.")
        @Column(name = "numero_celular", length = 15)
        private String telefono;

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$", message = "La contraseña debe contener al menos una mayúscula, un carácter especial y tener un mínimo de 8 dígitos.")
        @Column(name = "password")
        @NotNull(message = "Este campo no puede ser nulo")
        private String password;

        @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
        @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
        private Set<Role> roles; // para que el rol sea único
}
