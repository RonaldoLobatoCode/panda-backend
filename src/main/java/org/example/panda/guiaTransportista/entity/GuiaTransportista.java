package org.example.panda.guiaTransportista.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.example.panda.trabajador.entities.Trabajador;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guias_transportista", uniqueConstraints = { @UniqueConstraint(columnNames = { "id_trabajador" }) })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuiaTransportista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "serie_guia", nullable = false)
    private String serieGuia;

    @Column(name = "numero_guia", nullable = false)
    private Integer numeroGuia;

    @Column(name = "partida", nullable = false)
    private String partida;

    @Column(name = "llegada", nullable = false)
    private String llegada;

    @Column(name = "fecha_emision", nullable = false)
    private Timestamp fechaEmision;

    @Column(name = "fecha_traslado", nullable = false)
    private Date fechaTraslado;

    @Column(name = "remitente_ruc", nullable = false)
    private String remitenteRuc;

    @Column(name = "remitente_razonsocial", nullable = false)
    private String remitenteRazonSocial;

    @Column(name = "remitente_direccion")
    private String remitenteDireccion;

    @Column(name = "destinatario_ruc", nullable = false)
    private String destinatarioRuc;

    @Column(name = "destinatario_razonsocial", nullable = false)
    private String destinatarioRazonSocial;

    @Column(name = "destinatario_direccion")
    private String destinatarioDireccion;

    @Column(name = "peso_carga", nullable = false)
    private BigDecimal pesoCarga;

    @Column(name = "num_doc_chofer", nullable = false)
    private String numDocChofer;

    @Column(name = "nombre_chofer", nullable = false)
    private String nombreChofer;

    @Column(name = "placa_vehiculo", nullable = false)
    private String placaVehiculo;

    @Column(name = "ruc_pagador_del_flete", nullable = false)
    private String rucPagadorDelFlete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador")
    private Trabajador trabajador;
}