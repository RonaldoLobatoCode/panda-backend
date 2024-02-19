package org.example.panda.citas.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.citas.dtos.CitasViajeDto;
import org.example.panda.citas.dtos.CitasViajeResponse;
import org.example.panda.citas.repository.CitasViajeRepository;
import org.example.panda.citas.services.ICitasViajeService;
import org.example.panda.conductor.dtos.ConductorDto;
import org.example.panda.conductor.dtos.ConductorResponse;
import org.example.panda.utilities.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class CitasViajeController {

    private final ICitasViajeService citasViajeService;

    @GetMapping("/citas")
    public ResponseEntity<CitasViajeResponse> listCitasViajes(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        CitasViajeResponse citasViajeResponse = citasViajeService.listCitasViaje(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(citasViajeResponse);
    }

    @GetMapping("/cita/{id}")
    public ResponseEntity<CitasViajeDto> findCitasViajeById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(citasViajeService.listCitasViajeById(id), HttpStatus.OK);
    }

    @PostMapping("/cita")
    public ResponseEntity<CitasViajeDto> createCitasViaje(@Valid @RequestBody CitasViajeDto citasViajeDto){
        return new ResponseEntity<>(citasViajeService.createCitasViaje(citasViajeDto), HttpStatus.CREATED);
    }

    @PutMapping("/cita/{id}")
    public ResponseEntity<CitasViajeDto> updateCitasViaje(@Valid @RequestBody CitasViajeDto citasViajeDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(citasViajeService.updateCitasViaje(id,citasViajeDto), HttpStatus.OK);
    }

    @DeleteMapping("/cita/{id}")
    public ResponseEntity<String> deleteCitasViaje(@PathVariable("id") Integer id){
        citasViajeService.deleteCitasViaje(id);
        return new ResponseEntity<>("La reservación de cita de viaje fue eliminado con exito", HttpStatus.NO_CONTENT);
    }
}
