package org.example.panda.factura.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaRequest;
import org.example.panda.factura.dtos.FacturaResponse;
import org.example.panda.factura.services.FacturaService;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.utilities.AppConstants;
import org.example.panda.utilities.ErrorDetalles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearerAuth")
public class FacturaController {

    private final FacturaService facturaService;
    @Operation(
            summary = "Gets all Facturas",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema()
                            )
                    )
            }
    )
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
    @Operation(
            summary = "Create Factura",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema()
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request / Invalid data",
                            responseCode = "400"
                    )
                    ,
                    @ApiResponse(
                            description = "Internal Server Error / existing data",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetalles.class)
                            )
                    )
            }
    )
    @PostMapping("/factura")
    public ResponseEntity<FacturaDto> createFactura(@Valid @RequestBody FacturaRequest facturaRequest) {
        FacturaDto facturaDto = facturaService
                .createFactura(facturaRequest);
        return new ResponseEntity<>(facturaDto, HttpStatus.CREATED);
    }

}
