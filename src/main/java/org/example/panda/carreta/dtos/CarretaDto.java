package org.example.panda.carreta.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarretaDto implements Serializable {

    private Integer id;

    @NotEmpty(message = "La marca no puede estar vacio o nulo.")
    @Size(min = 2, max = 50, message = "La marca tiene que tener como mínimo 2 caracteres y como máximo 50 caracteres.")
    private String marca;

    @NotNull(message = "La capacidad de carga no puede ser nula.")
    @DecimalMin(value = "10000", inclusive = true, message = "La capacidad de carga debe ser mayor o igual que 10,000.")
    private BigDecimal capacidadCarga;
    @NotNull(message = "La placa no puede ser nula")
    @Size(min = 15, max = 15, message = "El número de placa debe tener exactamente 15 caracteres.")
    private String placa;
    @NotNull(message = "El año de fabricación no puede ser nulo.")
    @Min(value = 1960, message = "El año de fabricación debe ser mayor o igual a 1960.")
    @Max(value = 2025, message = "El año de fabricación debe ser menor o igual a 2025.")
    private int anoFabricacion;
}
