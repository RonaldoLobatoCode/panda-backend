package org.example.panda.trabajador.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.panda.trabajador.entities.Cargo;
import org.example.panda.trabajador.entities.EstadoCivil;
import org.example.panda.trabajador.entities.Genero;
import org.example.panda.trabajador.entities.Nacionalidad;
import org.example.panda.utilities.EstadoEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrabajadorDto implements Serializable {
    private Integer id;
    @NotBlank(message = "El nombre no puede estar vacio o nulo.")
    @Size(min = 2, message = "El nombre tiene que tener como mínimo 2 caracteres.")
    private String nombres;
    @NotEmpty(message = "El apellido no puede estar vacio o nulo.")
    @Size(min = 2, message = "El apellido tiene que tener más de 2 caracteres.")
    private String apellidos;
    @Size(min = 6, message = "El documento de identidad tiene que tener más de 6 caracteres.")
    private String numIdentidad;

    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    @NotNull(message = "La fecha no puede estar vacía")
    private Timestamp fechaNacimiento;

    @NotNull(message = "El género no puede estar vacío.")
    private Genero genero;

    @NotNull(message = "El estado civil no puede estar vacío.")
    private EstadoCivil estadoCivil;

    @NotNull(message = "La nacionalidad no puede estar vacía.")
    private Nacionalidad nacionalidad;

    @NotNull(message = "La dirección de residencia no puede estar vacía.")
    @Size(min = 3,message = "La dirección tiene que tener más de 3 caracteres.")
    private String direccionResidencia;

    @Pattern(regexp = "\\d{8,15}", message = "El número de teléfono debe tener entre 8 y 15 dígitos.")
    private String telefono;

    @Email(message = "El formato del correo electrónico no es válido.")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotNull(message = "El cargo no puede estar vacío.")
    private Cargo cargo;


    @Past(message = "La fecha de ingreso debe ser en el pasado.")
    private Timestamp fechaIngreso;

    @NotEmpty(message = "El número de cuenta bancaria no puede estar vacío.")
    @Size(min = 14, max = 14, message = "La cuenta bancaria debe tener exactamente 14 caracteres.")
    private String numCuentaBancaria;
    @NotNull(message = "El estado no puede estar vacío")
    @Pattern(regexp = "activo|inactivo", message = "El estado debe ser 'activo' o 'inactivo'")
    private String estado;

    private Integer idUser;
}

