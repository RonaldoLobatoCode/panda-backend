package org.example.panda.citas.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.panda.conductor.entities.Conductor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitasViajeDto implements Serializable {

    private Integer id;

    @Past(message = "La reservación de fecha de cita debe ser en el pasado.")
    @NotNull(message = "La reservación de fecha de cita no puede estar vacía")
    private LocalDate fechaCita;

    @NotNull(message = "El origen no puede estar vacía.")
    private String origen;

    @NotNull(message = "El destino no puede estar vacía.")
    private String destino;

    @NotNull(message = "El conductor no puede estar vacía.")
    private Conductor conductor;

}
