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
@Table(name = "generos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombreGenero"})})
public class Genero implements Serializable {
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
