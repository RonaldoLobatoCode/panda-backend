package org.example.panda.carreta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.carreta.dtos.CarretaResponse;
import org.example.panda.carreta.services.ICarretaService;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.utilities.AppConstants;
import org.example.panda.utilities.ErrorDetalles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearerAuth")
public class CarretaController {

    private final ICarretaService carretaService;
    @Operation(
            summary = "Gets all carretas with active status",
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
    @GetMapping("/carretas")
    public ResponseEntity<CarretaResponse> listCarretas(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        CarretaResponse carretaResponse = carretaService.listCarreta(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(carretaResponse);
    }
    @Operation(
            summary = "Gets user by id",
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
    @GetMapping("/carreta/{id}")
    public ResponseEntity<CarretaDto> findCarretaByID(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(carretaService.listCarretaById(id), HttpStatus.OK);
    }
    @Operation(
            summary = "Create carreta",
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
    @PostMapping("/carreta")
    public ResponseEntity<CarretaDto> createCarreta(@Valid @RequestBody CarretaDto carretaDto){
        return new ResponseEntity<>(carretaService.createCarreta(carretaDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update carreta",
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
    @PutMapping("/carreta/{id}")
    public ResponseEntity<CarretaDto> updateCarreta(@Valid @RequestBody CarretaDto carretaDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(carretaService.updateCarreta(id,carretaDto), HttpStatus.OK);
    }
    @Operation(
            summary = "Delete carreta",
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
    @DeleteMapping("/carreta/{id}")
    public ResponseEntity<String> deleteCarreta(@PathVariable("id") Integer id){
        carretaService.deleteCarreta(id);
        return new ResponseEntity<>("Carretera eliminado con exito", HttpStatus.NO_CONTENT);
    }
}
