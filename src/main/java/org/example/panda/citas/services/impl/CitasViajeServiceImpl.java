package org.example.panda.citas.services.impl;

import lombok.AllArgsConstructor;
import org.example.panda.citas.dtos.CitasViajeDto;
import org.example.panda.citas.dtos.CitasViajeResponse;
import org.example.panda.citas.entity.CitasViaje;
import org.example.panda.citas.repository.CitasViajeRepository;
import org.example.panda.citas.services.ICitasViajeService;
import org.example.panda.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CitasViajeServiceImpl implements ICitasViajeService {

    private final ModelMapper modelMapper;

    private final CitasViajeRepository citasViajeRepository;

    @Override
    public CitasViajeDto createCitasViaje(CitasViajeDto citasViajeDto) {
        return citasViajeEntityToDto(citasViajeRepository.save(citasViajeDtoToEntity(citasViajeDto)));
    }

    @Override
    public CitasViajeResponse listCitasViaje(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<CitasViaje> citasViajes = citasViajeRepository.findAll(pageable);

        List<CitasViaje> CitasViajeList = citasViajes.getContent();
        List<CitasViajeDto> contenido = CitasViajeList.stream().map(this::citasViajeEntityToDto).collect(Collectors.toList());

        return CitasViajeResponse.builder()
                .contenido(contenido)
                .numeroPagina(citasViajes.getNumber())
                .medidaPagina(citasViajes.getSize())
                .totalElementos(citasViajes.getTotalElements())
                .totalPaginas(citasViajes.getTotalPages())
                .ultima(citasViajes.isLast())
                .build();
    }

    @Override
    public CitasViajeDto listCitasViajeById(Integer id) {
        CitasViaje citasViaje = citasViajeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("CitasViaje","id",id));
        return citasViajeEntityToDto(citasViaje);
    }

    @Override
    public CitasViajeDto updateCitasViaje(Integer id, CitasViajeDto citasViajeDto) {
        if(citasViajeDto.getId() == null || id.equals(citasViajeDto.getId())){
            CitasViaje findCitsViaje = citasViajeRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("CitasViaje","id",id));
            citasViajeDto.setId(findCitsViaje.getId());
            return citasViajeEntityToDto(citasViajeRepository.save(citasViajeDtoToEntity(citasViajeDto)));
        }
        throw new IllegalArgumentException("Existe una discrepancia entre el ID proporcionado en la URL y el ID del registro correspondiente.");
    }

    @Override
    public void deleteCitasViaje(Integer Id) {
        CitasViaje citasViaje = citasViajeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("CitasViaje","id",id));
        citasViajeRepository.delete(citasViaje);
    }

    //Convertir de DTO a entidad
    private CitasViajeDto citasViajeEntityToDto(CitasViaje citasViaje){
        return modelMapper.map(citasViaje, CitasViajeDto.class);
    }

    private CitasViaje citasViajeDtoToEntity(CitasViajeDto citasViajeDto){
        return modelMapper.map(citasViajeDto, CitasViaje.class);
    }
}
