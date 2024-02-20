package org.example.panda.item.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.item.dtos.ItemDto;
import org.example.panda.item.dtos.ItemResponse;
import org.example.panda.item.entity.Item;
import org.example.panda.item.repository.ItemRepository;
import org.example.panda.item.services.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        // TODO Auto-generated method stub
        return itemEntityToDto(itemRepository.save(itemDtoToEntity(itemDto)));
    }

    @Override
    public ItemResponse listItem(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        // TODO Auto-generated method stub
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<Item> items = itemRepository.findAll(pageable);

        List<Item> ItemList = items.getContent();
        List<ItemDto> contenido = ItemList.stream().map(this::itemEntityToDto).collect(Collectors.toList());

        return ItemResponse.builder()
                .contenido(contenido)
                .numeroPagina(items.getNumber())
                .medidaPagina(items.getSize())
                .totalElementos(items.getTotalElements())
                .totalPaginas(items.getTotalPages())
                .ultima(items.isLast())
                .build();
    }

    @Override
    public ItemDto listItemById(Integer id) {
        // TODO Auto-generated method stub
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        return itemEntityToDto(item);
    }

    @Override
    public ItemDto updateItem(Integer id, ItemDto itemDto) {
        // TODO Auto-generated method stub
        if (itemDto.getId() == null || id.equals(itemDto.getId())) {
            Item findItem = itemRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
            itemDto.setId(findItem.getId());
            return itemEntityToDto(itemRepository.save(itemDtoToEntity(itemDto)));
        }
        throw new IllegalArgumentException(
                "Existe una discrepancia entre el ID proporcionado en la URL y el ID del registro correspondiente.");
    }

    @Override
    public void deleteItem(Integer id) {
        // TODO Auto-generated method stub
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        itemRepository.delete(item);
    }

    // Convertir de DTO a Entidad
    private ItemDto itemEntityToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    private Item itemDtoToEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }
}
