package org.example.panda.conductor.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.panda.camion.entity.Camion;
import org.example.panda.conductor.entities.TipoLicencia;
import org.example.panda.trabajador.entities.Trabajador;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConductorDto implements Serializable {

    private Integer id;

    @NotNull(message = "El trabajador no puede estar vacío.")
    private Trabajador trabajador;

    @NotNull(message = "El tipo de licencia no puede estar vacío.")
    private TipoLicencia tipoLicencia;

    @NotNull(message = "El camión no puede estar vacío.")
    private Camion camion;

    private boolean certConducirCamion;

    private boolean certPsicofisico;

    private boolean certMecanicaBasica;

    private boolean certPrimerosAuxilios;

    private boolean certSeguridadVial;
}
