package org.example.panda.factura.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import org.example.panda.aplicationSecurity.persistence.entities.User;
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

    private String serieGuia;

    private Integer numeroFactura;

    private Timestamp fechaEmision;

    private BigDecimal subTotal;

    private BigDecimal igv;

    private BigDecimal montoTotal;

    private String clienteRuc;
    private String clienteRazonSocial;

    private String clienteDireccion;

    private String observacion;

    private Set<Item> items;
}
