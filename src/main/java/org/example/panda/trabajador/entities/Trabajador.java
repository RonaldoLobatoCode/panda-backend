package org.example.panda.trabajador.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trabajadores", uniqueConstraints = {})
@Builder
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos", nullable = false, length = 255)
    private String apellidos;

    @Column(name = "num_identidad", unique = true, nullable = false, length = 20)
    private String numIdentidad;

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id", nullable = false)
    private EstadoCivil estadoCivil;

    @ManyToOne
    @JoinColumn(name = "nacionalidad_id", nullable = false)
    private Nacionalidad nacionalidad;

    @Column(name = "direccion_residencia", length = 255)
    private String direccionResidencia;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "cargo_puesto_id", nullable = false)
    private Cargo cargoPuesto;

    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;

    @Column(name = "num_cuenta_bancaria", length = 14)
    private String numCuentaBancaria;

}
