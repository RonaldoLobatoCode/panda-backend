package org.example.panda.conductor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.dtos.CamionResponse;
import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.conductor.dtos.ConductorDto;
import org.example.panda.conductor.dtos.ConductorResponse;
import org.example.panda.conductor.services.IConductorService;
import org.example.panda.utilities.AppConstants;
import org.example.panda.utilities.ErrorDetalles;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearerAuth")
public class ConductorController {

    private final IConductorService conductorService;
    @Operation(
            summary = "Gets all conductores",
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
    @GetMapping("/conductores")
    public ResponseEntity<ConductorResponse> listConductores(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        ConductorResponse conductorResponse = conductorService.listConductor(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(conductorResponse);
    }
    @Operation(
            summary = "Get conductor by id",
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
    @GetMapping("/conductor/{id}")
    public ResponseEntity<ConductorDto> findConductorById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(conductorService.listConductorById(id), HttpStatus.OK);
    }
    @Operation(
            summary = "Create conductor",
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
    @PostMapping("/conductor")
    public ResponseEntity<?> createConductor(@Valid @RequestBody ConductorDto conductorDto) {
            ConductorDto createdConductor = conductorService.createConductor(conductorDto);
            return new ResponseEntity<>(createdConductor, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update conductor",
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
    @PutMapping("/conductor/{id}")
    public ResponseEntity<ConductorDto> updateConductor(@Valid @RequestBody ConductorDto conductorDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(conductorService.updateConductor(id,conductorDto), HttpStatus.OK);
    }
    @Operation(
            summary = "Delete conductor",
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
    @DeleteMapping("/conductor/{id}")
    public ResponseEntity<String> deleteCarreta(@PathVariable("id") Integer id){
        conductorService.deleteConductor(id);
        return new ResponseEntity<>("Conductor eliminado con exito", HttpStatus.NO_CONTENT);
    }
}















