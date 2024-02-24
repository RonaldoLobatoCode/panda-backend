package org.example.panda.factura.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import org.example.panda.aplicationSecurity.persistence.entities.Role;
import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.item.entity.Item;


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

    @Column(name = "serie_factura", nullable = false)
    private String serieFactura;

    @Column(name = "numero_factura")
    private Integer numeroFactura;

    @Column(name = "fecha_emision")
    private Timestamp fechaEmision;
    @PrePersist
    protected void onCreate() {
        // Verificar si la fecha de ingreso ya está establecida
        if (this.fechaEmision == null) {
            // Si la fecha de ingreso no está establecida, establecerla como la fecha y hora
            // actual
            this.fechaEmision = new Timestamp(System.currentTimeMillis());
        }
    }

    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "igv", nullable = false)
    private BigDecimal igv;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;
    @Column(name = "cliente_ruc", nullable = false)
    private String clienteRuc;
    @Column(name = "cliente_razonSocial", nullable = false, length = 255)
    private String clienteRazonSocial;

    @Column(name = "cliente_direccion", length = 255)
    private String clienteDireccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_id", nullable = false)
    private GuiaTransportista guiaTransportista;
    @Column(name = "OBSERVACION", nullable = false, length = 255)
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Item.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "factura_items",
            joinColumns = @JoinColumn(name = "id_factura", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_item", referencedColumnName = "id"))
    private Set<Item> items;
}
