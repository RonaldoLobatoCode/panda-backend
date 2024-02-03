package org.example.panda.carreta.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.carreta.dtos.CarretaResponse;
import org.example.panda.carreta.services.ICarretaService;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.utilities.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/carretas")
@AllArgsConstructor
public class CarretaController {

    private final ICarretaService carretaService;

    @GetMapping
    public ResponseEntity<CarretaResponse> listCarretas(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        CarretaResponse carretaResponse = carretaService.listCarreta(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return ResponseEntity.ok(carretaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarretaDto> findCarretaByID(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(carretaService.listCarretaById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarretaDto> createCarreta(@Valid @RequestBody CarretaDto carretaDto){
        return new ResponseEntity<>(carretaService.createCarreta(carretaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarretaDto> updateCarreta(@Valid @RequestBody CarretaDto carretaDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(carretaService.updateCarreta(id,carretaDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarreta(@PathVariable("id") Integer id){
        carretaService.deleteCarreta(id);
        return new ResponseEntity<>("Carretera eliminado con exito", HttpStatus.NO_CONTENT);
    }
}
