package org.example.panda.trabajador.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cargos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre_cargo"})})
public class Cargo implements Serializable {
    public enum CargoEnum {
        Conductor_de_Camion,
        Coordinador_de_Logistica,
        Mecanico_de_Vehiculos,
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

    /*
    @JsonBackReference/*ignora la serialización, evitamos el error de json
    @OneToMany(mappedBy = "cargo")
    private List<Trabajador> trabajadores= new ArrayList<>();
    */
}
