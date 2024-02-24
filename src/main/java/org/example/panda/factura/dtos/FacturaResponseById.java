package org.example.panda.factura.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.panda.item.entity.Item;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturaResponseById {
    private Integer id;

    private String serieFactura;

    private Integer numeroFactura;

    private Timestamp fechaEmision;

    private BigDecimal subTotal;

    private BigDecimal igv;

    private BigDecimal montoTotal;

    private String clienteRuc;

    private String clienteRazonSocial;

    private String clienteDireccion;

    private String observacion;

    private String serieGuia;

    private int numeroGuia;

    private Set<Item> items;
}
