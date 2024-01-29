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
@Table(name = "nacionalidades", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre_nacionalidad"})})
public class Nacionalidad implements Serializable {
    public enum NacionalidadEnum {
        Argentina,
        Brasileña,
        Chilena,
        Colombiana,
        Mexicana,
        Peruana,
        Española,
        Estadounidense,
        Otra
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_nacionalidad", nullable = false)
    private NacionalidadEnum nombreNacionalidad;
}
