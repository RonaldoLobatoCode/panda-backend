package org.example.panda.guiaTransportista.dtos;

import lombok.*;
import org.example.panda.aplicationSecurity.persistence.entities.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
@Getter
@Setter
@Builder
public class GuiaTransportistaResponseById {
    private Integer id;


    private String serieGuia;

    private Integer numeroGuia;

    private String partida;

    private String llegada;

    private Timestamp fechaEmision;


    private Date fechaTraslado;

    private String remitenteRuc;

    private String remitenteRazonSocial;

    private String remitenteDireccion;

    private String destinatarioRuc;

    private String destinatarioRazonSocial;

    private String destinatarioDireccion;

    private BigDecimal pesoCarga;

    private String numDocChofer;

    private String nombreChofer;

    private String placaVehiculo;

    private String rucPagadorDelFlete;
}
