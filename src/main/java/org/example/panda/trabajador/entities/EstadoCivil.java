package org.example.panda.trabajador.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "estados_civiles", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombreEstadoCivil"})})
public class EstadoCivil {
    private enum EstadoCivilEnum {
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
