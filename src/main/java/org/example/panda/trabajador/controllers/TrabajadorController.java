package org.example.panda.trabajador.controllers;

import jakarta.validation.Valid;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.services.ITrabajadorService;
import org.example.panda.utilities.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TrabajadorController {
    private final ITrabajadorService trabajadorService;

    public TrabajadorController(ITrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }
    @GetMapping("/trabajadores")
    public ResponseEntity<TrabajadorResponse> listTrabajadores(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir){
        return new ResponseEntity<>(trabajadorService.listTrabajadores(numeroDePagina,medidaDePagina, ordenarPor, sortDir), HttpStatus.OK);
    }
    @GetMapping("/trabajador/{id}")
    public ResponseEntity<TrabajadorDto> findTrabajadorById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(trabajadorService.listTrabajadorById(id), HttpStatus.OK);
    }

    @PostMapping("trabajador")
    public ResponseEntity<TrabajadorDto> createTrabajadores(@Valid @RequestBody TrabajadorDto trabajadorDto){
        return new ResponseEntity<>(trabajadorService.createTrabajador(trabajadorDto), HttpStatus.CREATED);
    }
    @PutMapping("trabajador/{id}")
    public ResponseEntity<TrabajadorDto> updateTrabajador(@Valid @RequestBody TrabajadorDto trabajadorDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(trabajadorService.updateTrabajador(id, trabajadorDto), HttpStatus.OK);
    }

    @DeleteMapping("trabajador/{id}")
    public ResponseEntity<String> deleteTrabajador(@PathVariable("id") Integer id){
        trabajadorService.deleteTrabajador(id);
        return new ResponseEntity<>("Trabajador eliminado con éxito", HttpStatus.NO_CONTENT);
    }
}
