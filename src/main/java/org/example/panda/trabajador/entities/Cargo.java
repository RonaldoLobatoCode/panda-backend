package org.example.panda.trabajador.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cargos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombreCargo"})})
public class Cargo {
    public enum CargoEnum {
        Conductor_de_Camión,
        Coordinador_de_Logística,
        Mecánico_de_Vehículos,
        Gerente_de_Flota,
        Especialista_en_Seguridad_del_Transporte,
        Representante_de_Servicio_al_Cliente,
        Ingeniero_de_Sistemas,
        Gerente_de_Recursos_Humanos,
        Representante_de_Ventas,
        CEO_Director_Ejecutivo
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_cargo", nullable = false)
    private CargoEnum nombreCargo;
}
