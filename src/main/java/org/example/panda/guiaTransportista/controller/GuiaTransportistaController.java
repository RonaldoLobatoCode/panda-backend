package org.example.panda.guiaTransportista.controller;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaRequest;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponseById;
import org.example.panda.guiaTransportista.services.IGuiaTransportistaService;
import org.example.panda.utilities.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GuiaTransportistaController {

    private final IGuiaTransportistaService guiaTransportistaService;
    @GetMapping("/guia-transportistas")
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

    @GetMapping("/guia-transportista/{id}")
    public ResponseEntity<GuiaTransportistaResponseById> findGuiaTransportistaById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(guiaTransportistaService.listGuiaTransportistaById(id), HttpStatus.OK);
    }
    @PostMapping("/guia-transportista")
    public ResponseEntity<?> createGuiaTransportista(@Valid @RequestBody GuiaTransportistaRequest request) {
            GuiaTransportistaDto createdGuiaTransportista = guiaTransportistaService
                    .createGuiaTransportista(request);
            return new ResponseEntity<>(createdGuiaTransportista, HttpStatus.CREATED);
    }
}
