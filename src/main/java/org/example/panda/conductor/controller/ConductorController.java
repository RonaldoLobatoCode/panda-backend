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
    public ResponseEntity<ConductorDto> createConductor(@Valid @RequestBody ConductorDto conductorDto){
        return new ResponseEntity<>(conductorService.createConductor(conductorDto), HttpStatus.CREATED);
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
}















