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

    @Column(name = "doc_identidad", unique = true, nullable = false, length = 20)
    private String numIdentidad;

    @Column(name = "nombres", nullable = false, length = 50)
    private String nombres;


    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellidos;

    @Column(name = "email", length = 255)

    private String email;


    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "numero_celular", length = 15)
    private String telefono;

    private String estado;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles; //para que el rol sea único
    @Transient //anotacion para que no lo lea la bd, es un atributo utilitario
    private boolean admin;
}
