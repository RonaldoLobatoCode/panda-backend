package org.example.panda.guiaTransportista.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaRequest;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponseById;
import org.example.panda.guiaTransportista.services.IGuiaTransportistaService;
import org.example.panda.utilities.AppConstants;
import org.example.panda.utilities.ErrorDetalles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearerAuth")
public class GuiaTransportistaController {

    private final IGuiaTransportistaService guiaTransportistaService;
    @Operation(
            summary = "Gets all Guias Transportistas",
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
    @GetMapping("/guia-transportistas")
    public ResponseEntity<GuiaTransportistaResponse> listGuiTransportista(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        GuiaTransportistaResponse guiaTransportistaResponse = guiaTransportistaService.listGuiaTransportista(
                numeroDePagina, medidaDePagina,
                ordenarPor,
                sortDir);
        return ResponseEntity.ok(guiaTransportistaResponse);
    }
    @Operation(
            summary = "Get Guia transportista by id",
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
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetalles.class)
                            )
                    )
            }
    )
    @GetMapping("/guia-transportista/{id}")
    public ResponseEntity<GuiaTransportistaResponseById> findGuiaTransportistaById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(guiaTransportistaService.listGuiaTransportistaById(id), HttpStatus.OK);
    }
    @Operation(
            summary = "Create Guia Transportista",
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
    @PostMapping("/guia-transportista")
    public ResponseEntity<?> createGuiaTransportista(@Valid @RequestBody GuiaTransportistaRequest request) {
            GuiaTransportistaResponseById createdGuiaTransportista = guiaTransportistaService
                    .createGuiaTransportista(request);
            return new ResponseEntity<>(createdGuiaTransportista, HttpStatus.CREATED);
    }
}
