package org.example.panda.factura.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaRequest;
import org.example.panda.factura.dtos.FacturaResponse;
import org.example.panda.factura.services.FacturaService;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.utilities.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FacturaController {

    private final FacturaService facturaService;
    @GetMapping("/facturas")
    public ResponseEntity<FacturaResponse> listGuiTransportista(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        FacturaResponse facturas = facturaService.listFacturas(
                numeroDePagina, medidaDePagina,
                ordenarPor,
                sortDir);
        return ResponseEntity.ok(facturas);
    }
    @PostMapping("/factura")
    public ResponseEntity<FacturaDto> createFactura(@Valid @RequestBody FacturaRequest facturaRequest) {
        FacturaDto facturaDto = facturaService
                .createFactura(facturaRequest);
        return new ResponseEntity<>(facturaDto, HttpStatus.CREATED);
    }

}
