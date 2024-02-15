package org.example.panda.conductor.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.dtos.CamionResponse;
import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.conductor.dtos.ConductorDto;
import org.example.panda.conductor.dtos.ConductorResponse;
import org.example.panda.conductor.services.IConductorService;
import org.example.panda.utilities.AppConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/conductores")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ConductorController {

    private final IConductorService conductorService;

    @GetMapping
    public ResponseEntity<ConductorResponse> listConductores(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        ConductorResponse conductorResponse = conductorService.listConductor(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(conductorResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConductorDto> findConductorById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(conductorService.listConductorById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createConductor(@Valid @RequestBody ConductorDto conductorDto) {
        try {
            ConductorDto createdConductor = conductorService.createConductor(conductorDto);
            return new ResponseEntity<>(createdConductor, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            String customMessage = generateCustomErrorMessage(e);
            return ResponseEntity.badRequest().body(customMessage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConductorDto> updateConductor(@Valid @RequestBody ConductorDto conductorDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(conductorService.updateConductor(id,conductorDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarreta(@PathVariable("id") Integer id){
        conductorService.deleteConductor(id);
        return new ResponseEntity<>("Conductor eliminado con exito", HttpStatus.NO_CONTENT);
    }

    private String generateCustomErrorMessage(DataIntegrityViolationException e) {
        if (e.getMessage().contains("El trabajador ya está asignado a un conductor.")) {
            return "Error: El trabajador seleccionado ya está asignado a otro conductor.";
        } else if (e.getMessage().contains("El camión ya está asignado a un conductor.")) {
            return "Error: El camión seleccionado ya está asignado a otro conductor.";
        } else {
            return "Error: La entrada ya existe o viola una restricción de unicidad.";
        }
    }
}















