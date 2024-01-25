package org.example.panda.trabajador.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "generos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombreGenero"})})
public class Genero {
    private enum GeneroEnum {
        Masculino,
        Femenino,
        Otro
    }
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_genero", nullable = false)
    private GeneroEnum nombreGenero;
}
