package org.example.panda.conductor.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.panda.trabajador.entities.Trabajador;

import java.io.Serializable;

@Entity
@Table(name = "conductores_camion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conductor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "tipo_licencia_id", nullable = false)
    private TipoLicencia tipoLicencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "camion_id", nullable = false)
    private Camion camion;

    @Column(name = "cert_conducir_camion", nullable = false)
    private boolean certConducirCamion;

    @Column(name = "cert_psicofisico", nullable = false)
    private boolean certPsicofisico;

    @Column(name = "cert_mecanica_basica", nullable = false)
    private boolean certMecanicaBasica;

    @Column(name = "cert_primeros_auxilios", nullable = false)
    private boolean certPrimerosAuxilios;

    @Column(name = "cert_seguridad_vial", nullable = false)
    private boolean certSeguridadVial;
}

















