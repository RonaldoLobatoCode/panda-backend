package org.example.panda.factura.services;

import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaResponse;

public interface FacturaService {

    FacturaDto createFactura(FacturaDto facturaDto);

    FacturaResponse listFactura(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    FacturaDto listFacturaById(Integer id);

    FacturaDto updateFactura(Integer id, FacturaDto facturaDto);

    void deleteFactura(Integer id);
}
