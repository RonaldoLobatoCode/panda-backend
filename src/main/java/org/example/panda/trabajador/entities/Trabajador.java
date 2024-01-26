package org.example.panda.trabajador.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trabajadores", uniqueConstraints = {@UniqueConstraint(columnNames = {"numIdentidad"}),@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"numCuentaBancaria"})})
@Builder
public class Trabajador implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "nombres", nullable = false, length = 50)
    private String nombres;
    @Column(name = "apellidos", nullable = false, length = 50)
    private String apellidos;

    @Column(name = "num_identidad", unique = true, nullable = false, length = 20)
    private String numIdentidad;

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY) //carga perezosa, listar solo cuando lo necesitemos
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//1. para que ignore lzy exeption ya que esta devolvera ese error porque le pusimos la propiedad de lazy./2.Para que la seccion api rest ignore la propiedad aserializar en una cadena de serialización
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//1. para que ignore lzy exeption ya que esta devolvera ese error porque le pusimos la propiedad de lazy./2.Para que la seccion api rest ignore la propiedad aserializar en una cadena de serialización
    @JoinColumn(name = "estado_civil_id", nullable = false)
    private EstadoCivil estadoCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//1. para que ignore lzy exeption ya que esta devolvera ese error porque le pusimos la propiedad de lazy./2.Para que la seccion api rest ignore la propiedad aserializar en una cadena de serialización
    @JoinColumn(name = "nacionalidad_id", nullable = false)
    private Nacionalidad nacionalidad;

    @Column(name = "direccion_residencia", length = 255)
    private String direccionResidencia;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", length = 255)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//1. para que ignore lzy exeption ya que esta devolvera ese error porque le pusimos la propiedad de lazy./2.Para que la seccion api rest ignore la propiedad aserializar en una cadena de serialización
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;

    @Column(name = "num_cuenta_bancaria", length = 14)
    private String numCuentaBancaria;

}
