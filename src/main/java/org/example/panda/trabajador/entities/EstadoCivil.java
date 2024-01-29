package org.example.panda.trabajador.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "estados_civiles", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre_est_civil"})})
public class EstadoCivil implements Serializable {
    public enum EstadoCivilEnum {
        Soltero,
        Casado,
        Divorciado,
        Viudo
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_est_civil", nullable = false)
    private EstadoCivilEnum nombreEstadoCivil;
}
