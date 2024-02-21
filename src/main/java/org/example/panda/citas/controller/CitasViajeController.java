package org.example.panda.citas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.citas.dtos.CitasViajeDto;
import org.example.panda.citas.dtos.CitasViajeResponse;
import org.example.panda.citas.repository.CitasViajeRepository;
import org.example.panda.citas.services.ICitasViajeService;
import org.example.panda.conductor.dtos.ConductorDto;
import org.example.panda.conductor.dtos.ConductorResponse;
import org.example.panda.utilities.AppConstants;
import org.example.panda.utilities.ErrorDetalles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CitasViajeController {

    private final ICitasViajeService citasViajeService;
    @Operation(
            summary = "Gets all citas",
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
    @GetMapping("/citas")
    public ResponseEntity<CitasViajeResponse> listCitasViajes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        CitasViajeResponse citasViajeResponse = citasViajeService.listCitasViaje(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(citasViajeResponse);
    }
    @Operation(
            summary = "Get cita by id",
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
    @GetMapping("/cita/{id}")
    public ResponseEntity<CitasViajeDto> findCitasViajeById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(citasViajeService.listCitasViajeById(id), HttpStatus.OK);
    }
    @Operation(
            summary = "Create citas",
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
    @PostMapping("/cita")
    public ResponseEntity<CitasViajeDto> createCitasViaje(@Valid @RequestBody CitasViajeDto citasViajeDto){
        return new ResponseEntity<>(citasViajeService.createCitasViaje(citasViajeDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update cita",
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
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetalles.class)
                            )
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
    @PutMapping("/cita/{id}")
    public ResponseEntity<CitasViajeDto> updateCitasViaje(@Valid @RequestBody CitasViajeDto citasViajeDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(citasViajeService.updateCitasViaje(id,citasViajeDto), HttpStatus.OK);
    }
    @Operation(
            summary = "Delete cita",
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
    @DeleteMapping("/cita/{id}")
    public ResponseEntity<String> deleteCitasViaje(@PathVariable("id") Integer id){
        citasViajeService.deleteCitasViaje(id);
        return new ResponseEntity<>("La reservación de cita de viaje fue eliminado con exito", HttpStatus.NO_CONTENT);
    }
}
