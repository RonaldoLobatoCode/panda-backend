package org.example.panda.factura.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaRequest;
import org.example.panda.factura.services.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping("/factura")
    public ResponseEntity<FacturaDto> createFactura(@Valid @RequestBody FacturaRequest facturaRequest) {
        FacturaDto facturaDto = facturaService
                .createFactura(facturaRequest);
        return new ResponseEntity<>(facturaDto, HttpStatus.CREATED);
    }
}
