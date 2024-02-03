package org.example.panda.camion.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CamionResponse implements Serializable {

    private List<CamionDto> contenido;
    private int numeroPagina;
    private int medidaPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
}
