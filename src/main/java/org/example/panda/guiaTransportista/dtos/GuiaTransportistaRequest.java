package org.example.panda.guiaTransportista.dtos;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Getter
@Setter
@Builder
public class GuiaTransportistaRequest {
    private Integer id;
    @NotBlank(message = "La partida no puede estar vacía.")
    @Size(max = 100, message = "La partida debe tener como máximo 100 caracteres.")
    private String partida;
    @NotBlank(message = "La llegada no puede estar vacía.")
    @Size(max = 100, message = "La llegada debe tener como máximo 100 caracteres.")
    private String llegada;
    @NotNull(message = "La fecha de traslado no puede estar vacía.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp fechaTraslado;
    @NotBlank(message = "El RUC del remitente no puede estar vacío.")
    @Size(min =11 , max= 11, message = "El RUC del remitente debe tener 11 caracteres.")
    private String remitenteRuc;
    @NotBlank(message = "El RUC del destinatario no puede estar vacío.")
    @Size(min =11 , max= 11, message = "El RUC del remitente debe tener 11 caracteres.")
    private String destinatarioRuc;
    @NotBlank(message = "La razón social del destinatario no puede estar vacía.")
    @Size(max = 100, message = "La razón social del destinatario debe tener como máximo 100 caracteres.")
    private String destinatarioRazonSocial;
    @NotBlank(message = "Este registro no puede estar vacio.")
    @Size(max = 100, message = "La dirección del destinatario debe tener como máximo 100 caracteres.")
    private String destinatarioDireccion;
    @NotNull(message = "El peso de la carga no puede estar vacío.")
    @DecimalMin(value = "0.01", message = "El peso de la carga debe ser mayor que 0.")
    private BigDecimal pesoCarga;
    @NotBlank(message = "El número de documento del chofer no puede estar vacío.")
    @Size(min=8 ,max = 20, message = "El número de documento del chofer debe tener como máximo 20 caracteres.")
    private String numDocChofer;
    @NotBlank(message = "El RUC del pagador del flete no puede estar vacío.")
    @Size(min =11 , max= 11, message = "El RUC del pagador del flete debe tener 11 caracteres.")
    private String rucPagadorDelFlete;
    @NotBlank(message = "La placa del vehículo no puede estar vacía.")
    @Size(min = 15, max = 15, message = "El número de placa debe tener exactamente 15 caracteres.")
    private String placaVehiculo;
    private Long idUser;
}
