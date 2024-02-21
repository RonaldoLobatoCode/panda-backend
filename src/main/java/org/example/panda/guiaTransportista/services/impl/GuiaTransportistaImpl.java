package org.example.panda.guiaTransportista.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.guiaTransportista.repository.GuiaTransportistaRepository;
import org.example.panda.guiaTransportista.services.IGuiaTransportistaService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuiaTransportistaImpl implements IGuiaTransportistaService {

    private final ModelMapper modelMapper;

    private final GuiaTransportistaRepository guiaTransportistaRepository;

    @Override
    public GuiaTransportistaDto createGuiaTransportista(GuiaTransportistaDto guiaTransportistaDto) {

        boolean existsTrabajador = guiaTransportistaRepository
                .existsByTrabajadorId(guiaTransportistaDto.getTrabajador().getId());

        if (existsTrabajador) {
            throw new DataIntegrityViolationException("El trabajador ya está asignado a una guia.");

        }

        GuiaTransportista guiaEntity = guiaDtoToEntity(guiaTransportistaDto);
        GuiaTransportista save = guiaTransportistaRepository.save(guiaEntity);
        return guiaEntityToDto(save);
    }

    @Override
    public GuiaTransportistaResponse listGuiaTransportista(int numeroDePagina, int medidaDePagina, String ordenarPor,
            String sortDir) {
        // TODO Auto-generated method stub
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<GuiaTransportista> guiaTransportista = guiaTransportistaRepository.findAll(pageable);

        List<GuiaTransportista> GuiaTransportistaList = guiaTransportista.getContent();
        List<GuiaTransportistaDto> contenido = GuiaTransportistaList.stream().map(this::guiaEntityToDto)
                .collect(Collectors.toList());

        return GuiaTransportistaResponse.builder()
                .contenido(contenido)
                .numeroPagina(guiaTransportista.getNumber())
                .medidaPagina(guiaTransportista.getSize())
                .totalElementos(guiaTransportista.getTotalElements())
                .totalPaginas(guiaTransportista.getTotalPages())
                .ultima(guiaTransportista.isLast())
                .build();
    }

    @Override
    public GuiaTransportistaDto listGuiaTransportistaById(Integer id) {
        // TODO Auto-generated method stub
        GuiaTransportista guiaTransportista = guiaTransportistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GuiaTransportista", "id", id));
        return guiaEntityToDto(guiaTransportista);
    }

    @Override
    public GuiaTransportistaDto updateGuiaTransportista(Integer id, GuiaTransportistaDto guiaTransportistaDto) {
        // TODO Auto-generated method stub

        boolean existsTrabajador = guiaTransportistaRepository
                .existsByTrabajadorId(guiaTransportistaDto.getTrabajador().getId());

        if (existsTrabajador) {
            throw new DataIntegrityViolationException("El trabajador ya está asignado a una guia.");

        }
        if (guiaTransportistaDto.getId() == null || id.equals(guiaTransportistaDto.getId())) {
            GuiaTransportista findGuia = guiaTransportistaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("GuiaTransportista", "id", id));

            guiaTransportistaDto.setId(findGuia.getId());

            GuiaTransportista update = guiaTransportistaRepository.save(guiaDtoToEntity(guiaTransportistaDto));
            return guiaEntityToDto(update);
        } else {
            throw new IllegalArgumentException(
                    "Existe una discrepancia entre el ID proporcionado en la URL y el ID del registro correspondiente.");
        }
    }

    @Override
    public void deleteGuiaTransportista(Integer Id) {
        // TODO Auto-generated method stub
        GuiaTransportista guia = guiaTransportistaRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("GuiaTransportista", "id", Id));
        guiaTransportistaRepository.delete(guia);
    }

    private GuiaTransportistaDto guiaEntityToDto(GuiaTransportista guiaTransportista) {
        return modelMapper.map(guiaTransportista, GuiaTransportistaDto.class);
    }

    private GuiaTransportista guiaDtoToEntity(GuiaTransportistaDto guiaTransportistaDto) {
        return modelMapper.map(guiaTransportistaDto, GuiaTransportista.class);
    }
}
