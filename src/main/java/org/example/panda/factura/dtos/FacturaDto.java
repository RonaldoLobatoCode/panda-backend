package org.example.panda.factura.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.item.entity.Item;
import org.example.panda.trabajador.entities.Trabajador;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
public class FacturaDto implements Serializable {

    private Integer id;

    @NotBlank(message = "La serie de la guía no puede estar vacía.")
    @Size(max = 10, message = "La serie de la guía no puede superar los 10 caracteres.")
    private String serieGuia;

    @NotNull(message = "El número de factura no puede ser nulo.")
    private Integer numeroFactura;

    @NotNull(message = "La fecha de emisión no puede ser nula.")
    private Timestamp fechaEmision;

    @NotNull(message = "El subtotal no puede ser nulo.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El subtotal debe ser mayor que 0.")
    private BigDecimal subTotal;

    @NotNull(message = "El IGV no puede ser nulo.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El IGV debe ser mayor que 0.")
    private BigDecimal igv;

    @NotNull(message = "El monto total no puede ser nulo.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto total debe ser mayor que 0.")
    private BigDecimal montoTotal;

    @NotBlank(message = "La razón social del cliente no puede estar vacía.")
    @Size(max = 15, message = "La razón social del cliente no puede superar los 15 caracteres.")
    private String clienteRazonSocial;

    @Size(max = 255, message = "La dirección del cliente no puede superar los 255 caracteres.")
    private String clienteDireccion;

    @NotNull(message = "La guia de transportista no puede estar vacía.")
    private GuiaTransportista guiaId;

    @NotNull(message = "El item no puede estar vacía.")
    private Item itemId;

    @NotNull(message = "El trabajador no puede estar vacía.")
    private Trabajador idTrabajador;

}
