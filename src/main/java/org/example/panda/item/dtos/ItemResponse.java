package org.example.panda.item.dtos;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemResponse implements Serializable {

    private List<ItemDto> contenido;
    private int numeroPagina;
    private int medidaPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
}
