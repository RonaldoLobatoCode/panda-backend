package org.example.panda.factura.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.panda.item.entity.Item;

import java.util.List;


@Getter
@Setter
@Builder
public class FacturaRequest {

    private Integer id;
    @NotBlank(message = "El RUC del remitente no puede estar vacío.")
    @Size(min =11 , max= 11, message = "El RUC del remitente debe tener 11 caracteres.")
    private String clienteRuc;

    private String observacion;
    @NotBlank(message = "La serie de Guia de transportista no puede estar vacia")
    @Size(min =4 , max= 4, message = "La serie debe tener 4 dígitos")
    private String seguieGuia;
    @NotNull(message = "Número de guia no puede estar vacio")
    private int numeroGuia;
    private Long idUser;
    private List<Item> items;
}
