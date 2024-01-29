package org.example.panda.trabajador.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "generos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre_genero"})})
public class Genero implements Serializable {
    public enum GeneroEnum {
        Masculino,
        Femenino,
        Otro
    }
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_genero", nullable = false)
    private GeneroEnum nombreGenero;
}
