package org.example.panda.factura.services;

import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaRequest;
import org.example.panda.factura.dtos.FacturaResponse;

public interface FacturaService {

    FacturaDto createFactura(FacturaRequest request);

}
