package org.example.panda.guiaTransportista.controller;

import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.guiaTransportista.services.IGuiaTransportistaService;
import org.example.panda.utilities.AppConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/guia-transportistas")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GuiaTransportistaController {

    private final IGuiaTransportistaService guiaTransportistaService;

    @GetMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<GuiaTransportistaDto> findGuiaTransportistaById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(guiaTransportistaService.listGuiaTransportistaById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createGuiaTransportista(@Valid @RequestBody GuiaTransportistaDto guiaTransportistaDto) {
        try {
            GuiaTransportistaDto createdGuiaTransportista = guiaTransportistaService
                    .createGuiaTransportista(guiaTransportistaDto);
            return new ResponseEntity<>(createdGuiaTransportista, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            String customMessage = generateCustomErrorMessage(e);
            return ResponseEntity.badRequest().body(customMessage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuiaTransportistaDto> updateGuiaTransportista(
            @Valid @RequestBody GuiaTransportistaDto guiaTransportistaDto, @PathVariable("id") Integer id) {
        return new ResponseEntity<>(guiaTransportistaService.updateGuiaTransportista(id, guiaTransportistaDto),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGuiaTransportista(@PathVariable("id") Integer id) {
        guiaTransportistaService.deleteGuiaTransportista(id);
        return new ResponseEntity<>("Guia transportista eliminado con exito", HttpStatus.NO_CONTENT);
    }

    private String generateCustomErrorMessage(DataIntegrityViolationException e) {
        if (e.getMessage().contains("El trabajador ya está asignado a una guia de transportista.")) {
            return "Error: El trabajador seleccionado ya está asignado a otro guia de transportista.";
        } else {
            return "Error: El ID del trabajador ya existe en unos de nuestros registros o viola una restricción de unicidad.";
        }
    }
}
