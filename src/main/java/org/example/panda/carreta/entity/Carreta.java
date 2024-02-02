package org.example.panda.carreta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "carretas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carreta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "capacidad_carga", nullable = false)
    private BigDecimal capacidadCarga;

    @Column(name = "año_fabricacion", nullable = false)
    private int añoFabricacion;
}
