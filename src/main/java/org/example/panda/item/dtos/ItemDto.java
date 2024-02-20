package org.example.panda.item.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto implements Serializable {

    private Integer id;

    @NotEmpty(message = "La descripcion no puede estar vacio o nulo.")
    @Size(min = 25, max = 255, message = "La descripcion tiene que tener como mínimo 25 caracteres y como máximo 255 caracteres.")
    private String descripcion;

    @NotNull(message = "La cantidad no puede ser nula.")
    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    @Max(value = 1000, message = "Ingrese una cantidad valida")
    private int cantidad;

    @NotNull(message = "El precio unitario no puede estar vacio o nulo.")
    private BigDecimal precioUnitario;
}
