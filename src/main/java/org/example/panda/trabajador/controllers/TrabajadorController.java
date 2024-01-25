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
    public ResponseEntity<?> findTrabajadorById(@PathVariable("id") Integer id){
        Optional<TrabajadorDto> findTrabajador=trabajadorService.listTrabajadorById(id);
        return findTrabajador.isPresent() ? ResponseEntity.ok(findTrabajador.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    @PostMapping("trabajador")
    public ResponseEntity<TrabajadorDto> createTrabajadores(@Valid @RequestBody TrabajadorDto trabajadorDto){
        return new ResponseEntity<>(trabajadorService.createTrabajador(trabajadorDto), HttpStatus.CREATED);
    }
    @PutMapping("trabajador/{id}")
    public ResponseEntity<?> updateTrabajador(@Valid @RequestBody TrabajadorDto trabajadorDto, @PathVariable("id") Integer id){
        Optional<TrabajadorDto> optionalTrabajadorDto= trabajadorService.updateTrabajador(id, trabajadorDto);
        return optionalTrabajadorDto.isPresent()? ResponseEntity.ok(optionalTrabajadorDto.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no existe");
    }

}
