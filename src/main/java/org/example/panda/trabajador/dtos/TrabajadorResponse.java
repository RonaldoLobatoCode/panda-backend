package org.example.panda.trabajador.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TrabajadorResponse implements Serializable {
    private List<TrabajadorDto> contenido;
    private int numeroPagina;
    private int medidaPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
}
