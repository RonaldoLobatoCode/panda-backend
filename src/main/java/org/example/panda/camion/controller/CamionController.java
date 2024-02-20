package org.example.panda.camion.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.dtos.CamionResponse;
import org.example.panda.camion.services.ICamionService;
import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.utilities.AppConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CamionController {

    private final ICamionService camionService;

    @GetMapping("/camiones")
    public ResponseEntity<CamionResponse> listCamiones(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        CamionResponse camionResponse = camionService.listCamion(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(camionResponse);
    }

    @GetMapping("/camion/{id}")
    public ResponseEntity<CamionDto> findCamionById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(camionService.listCamionById(id), HttpStatus.OK);
    }

    @PostMapping("/camion")
    public ResponseEntity<?> createCamion(@Valid @RequestBody CamionDto camionDto) {
            CamionDto createdCamion = camionService.createCamion(camionDto);
            return new ResponseEntity<>(createdCamion, HttpStatus.CREATED);
    }

    @PutMapping("/camion/{id}")
    public ResponseEntity<?> updateCamion(@Valid @RequestBody CamionDto camionDto, @PathVariable("id") Integer id) {
            CamionDto updatedCamion = camionService.updateCamion(id, camionDto);
            return new ResponseEntity<>(updatedCamion, HttpStatus.OK);
    }


    @DeleteMapping("/camion/{id}")
    public ResponseEntity<String> deleteCamion(@PathVariable("id") Integer id){
        camionService.deleteCamion(id);
        return new ResponseEntity<>("Camion eliminado con exito", HttpStatus.NO_CONTENT);
    }
}



























