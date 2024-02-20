package org.example.panda.item.controller;

import org.example.panda.item.dtos.ItemDto;
import org.example.panda.item.dtos.ItemResponse;
import org.example.panda.item.services.ItemService;
import org.example.panda.utilities.AppConstants;
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
@RequestMapping(path = "api/items")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ItemResponse> listItems(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir) {
        ItemResponse itemsResponse = itemService.listItem(numeroDePagina, medidaDePagina, ordenarPor,
                sortDir);
        return ResponseEntity.ok(itemsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> findItemByID(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(itemService.listItemById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(itemService.createItem(itemDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@Valid @RequestBody ItemDto itemDto,
            @PathVariable("id") Integer id) {
        return new ResponseEntity<>(itemService.updateItem(id, itemDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") Integer id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item eliminado con exito", HttpStatus.NO_CONTENT);
    }
}
