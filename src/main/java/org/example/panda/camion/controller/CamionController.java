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
@RequestMapping(path = "api/camiones")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CamionController {

    private final ICamionService camionService;

    @GetMapping
    public ResponseEntity<CamionResponse> listCamiones(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        CamionResponse camionResponse = camionService.listCamion(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(camionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamionDto> findCamionById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(camionService.listCamionById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCamion(@Valid @RequestBody CamionDto camionDto) {
        try {
            CamionDto createdCamion = camionService.createCamion(camionDto);
            return new ResponseEntity<>(createdCamion, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error: La entrada ya existe o viola una restricción de unicidad.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCamion(@Valid @RequestBody CamionDto camionDto, @PathVariable("id") Integer id) {
        try {
            CamionDto updatedCamion = camionService.updateCamion(id, camionDto);
            return new ResponseEntity<>(updatedCamion, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error: La actualización viola una restricción de unicidad.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No se encontró ningún camión con el ID proporcionado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: El ID proporcionado en la URL no coincide con nuestros registros.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCamion(@PathVariable("id") Integer id){
        camionService.deleteCamion(id);
        return new ResponseEntity<>("Camion eliminado con exito", HttpStatus.NO_CONTENT);
    }
}



























