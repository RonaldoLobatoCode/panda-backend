package org.example.panda.camion.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.panda.carreta.entity.Carreta;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CamionDto implements Serializable {

    private Integer id;

    @NotEmpty(message = "La marca no puede estar vacio o nulo")
    @Size(min = 2, max = 50, message = "La marca tiene que tener como mínimo 2 caracteres y como máximo 50 caracteres.")
    private String marca;

    @NotEmpty(message = "El modelo no puede estar vacio o nulo")
    @Size(min = 2, max = 50, message = "EL modelo tiene que tener como mínimo 2 caracteres y como máximo 50 caracteres.")
    private String modelo;

    @NotNull(message = "El año de fabricación no puede ser nulo.")
    @Min(value = 1940, message = "El año de fabricación debe ser mayor o igual a 1940.")
    @Max(value = 2025, message = "El año de fabricación debe ser menor o igual a 2025.")
    private int anoFabricacion;

    @NotEmpty(message = "El número de placa no puede estar vacío.")
    @Size(min = 15, max = 15, message = "El número de placa debe tener exactamente 15 caracteres.")
    private String placa;

    private Carreta carreta;

}
