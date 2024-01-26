package org.example.panda.trabajador.dtos;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.panda.trabajador.entities.Cargo;
import org.example.panda.trabajador.entities.EstadoCivil;
import org.example.panda.trabajador.entities.Genero;
import org.example.panda.trabajador.entities.Nacionalidad;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrabajadorDto implements Serializable {
    private Integer id;
    @NotEmpty(message = "El nombre no puede estar vacio o nulo.")
    @Size(min = 2, message = "El nombre tiene que tener como mínimo 2 caracteres.")
    private String nombres;
    @NotEmpty(message = "El apellido no puede estar vacio o nulo.")
    @Size(min = 2, message = "El apellido tiene que tener más de 2 caracteres.")
    private String apellidos;
    @Size(min = 6, message = "El documento de identidad tiene que tener más de 6 caracteres.")
    private String numIdentidad;

    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    @NotNull(message = "La fecha no puede estar vacía")
    private Date fechaNacimiento;

    @NotNull(message = "El género no puede estar vacío.")
    private Genero genero;

    @NotNull(message = "El estado civil no puede estar vacío.")
    private EstadoCivil estadoCivil;

    @NotNull(message = "La nacionalidad no puede estar vacía.")
    private Nacionalidad nacionalidad;

    @NotEmpty(message = "La dirección de residencia no puede estar vacía.")
    @Size(min = 3,message = "La dirección tiene que tener más de 3 caracteres.")
    private String direccionResidencia;

    @Pattern(regexp = "\\d{8,15}", message = "El número de teléfono debe tener entre 8 y 15 dígitos.")
    private String telefono;

    @Email(message = "El formato del correo electrónico no es válido.")
    private String email;

    @NotNull(message = "El cargo no puede estar vacío.")
    private Cargo cargo;


    @Past(message = "La fecha de ingreso debe ser en el pasado.")
    @NotNull(message = "La fecha no puede estar vacía")
    private Date fechaIngreso;

    @NotEmpty(message = "El número de cuenta bancaria no puede estar vacío.")
    @Size(min = 14, max = 14, message = "La cuenta bancaria debe tener exactamente 14 caracteres.")
    private String numCuentaBancaria;
}
