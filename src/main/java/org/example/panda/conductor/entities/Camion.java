package org.example.panda.conductor.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "camiones", uniqueConstraints = {@UniqueConstraint(columnNames = {"placa"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Camion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    @Column(name = "año_fabricacion", nullable = false)
    private int añoFabricacion;

    @Column(name = "placa", unique = true, nullable = false, length = 15)
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "carreta_id", nullable = false)
    private Carreta carreta;

}
