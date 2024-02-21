package org.example.panda.factura.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.item.entity.Item;
import org.example.panda.trabajador.entities.Trabajador;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "serie_guia", nullable = false)
    private String serieGuia;

    @Column(name = "numero_factura", nullable = false)
    private Integer numeroFactura;

    @Column(name = "fecha_emision", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaEmision;

    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "igv", nullable = false)
    private BigDecimal igv;

    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "cliente_razonSocial", nullable = false, length = 255)
    private String clienteRazonSocial;

    @Column(name = "cliente_direccion", length = 255)
    private String clienteDireccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "guia_id", nullable = false)
    private GuiaTransportista guiaTransportista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

}
