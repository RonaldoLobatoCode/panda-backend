package org.example.panda.factura.services;

import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaRequest;
import org.example.panda.factura.dtos.FacturaResponse;
import org.example.panda.factura.dtos.FacturaResponseById;

public interface FacturaService {

    FacturaDto createFactura(FacturaRequest request);
    FacturaResponse listFacturas(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
}
