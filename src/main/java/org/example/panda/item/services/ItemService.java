package org.example.panda.item.services;

import org.example.panda.item.dtos.ItemDto;
import org.example.panda.item.dtos.ItemResponse;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto);

    ItemResponse listItem(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    ItemDto listItemById(Integer id);

    ItemDto updateItem(Integer id, ItemDto itemDto);

    void deleteItem(Integer id);
}
