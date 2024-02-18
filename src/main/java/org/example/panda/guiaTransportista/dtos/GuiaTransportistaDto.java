package org.example.panda.guiaTransportista.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.example.panda.trabajador.entities.Trabajador;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuiaTransportistaDto implements Serializable {

    private Integer id;

    @NotBlank(message = "La serie de la guía no puede estar vacía.")
    @Size(max = 10, message = "La serie de la guía debe tener como máximo 10 caracteres.")
    private String serieGuia;

    @NotNull(message = "El número de guía no puede estar vacío.")
    private Integer numeroGuia;

    @NotBlank(message = "La partida no puede estar vacía.")
    @Size(max = 100, message = "La partida debe tener como máximo 100 caracteres.")
    private String partida;

    @NotBlank(message = "La llegada no puede estar vacía.")
    @Size(max = 100, message = "La llegada debe tener como máximo 100 caracteres.")
    private String llegada;

    @NotNull(message = "La fecha de emisión no puede estar vacía.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "La fecha de emisión debe ser en el futuro.")
    private Timestamp fechaEmision;

    @NotNull(message = "La fecha de traslado no puede estar vacía.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "La fecha de traslado debe ser en el futuro.")
    private Date fechaTraslado;

    @NotBlank(message = "El RUC del remitente no puede estar vacío.")
    @Size(max = 15, message = "El RUC del remitente debe tener como máximo 15 caracteres.")
    private String remitenteRuc;

    @NotBlank(message = "La razón social del remitente no puede estar vacía.")
    @Size(max = 100, message = "La razón social del remitente debe tener como máximo 100 caracteres.")
    private String remitenteRazonSocial;

    @Size(max = 100, message = "La dirección del remitente debe tener como máximo 100 caracteres.")
    private String remitenteDireccion;

    @NotBlank(message = "El RUC del destinatario no puede estar vacío.")
    @Size(max = 15, message = "El RUC del destinatario debe tener como máximo 15 caracteres.")
    private String destinatarioRuc;

    @NotBlank(message = "La razón social del destinatario no puede estar vacía.")
    @Size(max = 100, message = "La razón social del destinatario debe tener como máximo 100 caracteres.")
    private String destinatarioRazonSocial;

    @Size(max = 100, message = "La dirección del destinatario debe tener como máximo 100 caracteres.")
    private String destinatarioDireccion;

    @NotNull(message = "El peso de la carga no puede estar vacío.")
    @DecimalMin(value = "0.01", message = "El peso de la carga debe ser mayor que 0.")
    private BigDecimal pesoCarga;

    @NotBlank(message = "El número de documento del chofer no puede estar vacío.")
    @Size(max = 20, message = "El número de documento del chofer debe tener como máximo 20 caracteres.")
    private String numDocChofer;

    @NotBlank(message = "El nombre del chofer no puede estar vacío.")
    @Size(max = 20, message = "El nombre del chofer debe tener como máximo 20 caracteres.")
    private String nombreChofer;

    @NotBlank(message = "La placa del vehículo no puede estar vacía.")
    @Size(max = 15, message = "La placa del vehículo debe tener como máximo 15 caracteres.")
    private String placaVehiculo;

    @NotBlank(message = "El RUC del pagador del flete no puede estar vacío.")
    @Size(max = 15, message = "El RUC del pagador del flete debe tener como máximo 15 caracteres.")
    private String rucPagadorDelFlete;

    @NotNull(message = "El ID del trabajador no puede estar vacío.")
    private Trabajador trabajador;
}