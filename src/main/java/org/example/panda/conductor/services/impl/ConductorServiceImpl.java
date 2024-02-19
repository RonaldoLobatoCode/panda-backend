package org.example.panda.conductor.services.impl;

import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.entity.Camion;
import org.example.panda.camion.repository.CamionRepository;
import org.example.panda.conductor.dtos.ConductorDto;
import org.example.panda.conductor.dtos.ConductorResponse;
import org.example.panda.conductor.entities.Conductor;
import org.example.panda.conductor.repositories.ConductorRepository;
import org.example.panda.conductor.services.IConductorService;
import org.example.panda.exceptions.ResourceNotFoundException;

import org.example.panda.trabajador.entities.Trabajador;
import org.example.panda.trabajador.repositories.TrabajadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class  ConductorServiceImpl implements IConductorService {

    private final ModelMapper modelMapper;

    private final ConductorRepository conductorRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final CamionRepository camionRepository;

    @Override
    public ConductorDto createConductor(ConductorDto conductorDto) {
        validarDatos(conductorDto);
        Conductor conductorEntity = conductorDtoToEntity(conductorDto);
        Conductor savedConductor = conductorRepository.save(conductorEntity);
        return conductorEntityToDto(savedConductor);
    }


    @Override
    public ConductorResponse listConductor(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

        Pageable pageable= PageRequest.of(numeroDePagina,medidaDePagina, sort);
        Page<Conductor> conductores = conductorRepository.findAll(pageable);

        List<Conductor> ConductoresList = conductores.getContent();
        List<ConductorDto> contenido= ConductoresList.stream().map(this::conductorEntityToDto).collect(Collectors.toList());
        return ConductorResponse.builder()
                .contenido(contenido)
                .numeroPagina(conductores.getNumber())
                .medidaPagina(conductores.getSize())
                .totalElementos(conductores.getTotalElements())
                .totalPaginas(conductores.getTotalPages())
                .ultima(conductores.isLast())
                .build();
    }

    @Override
    public ConductorDto listConductorById(Integer id) {
        Conductor conductor = conductorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Conductor", "id", id));
        return conductorEntityToDto(conductor);
    }

    @Override
    public ConductorDto updateConductor(Integer id, ConductorDto conductorDto) {

        if (conductorDto.getId() == null || id.equals(conductorDto.getId())) {
            Conductor existingConductor = conductorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Conductor", "id", id));
            validarDatos(conductorDto, id);
            conductorDto.setId(existingConductor.getId());

            Conductor updatedConductor = conductorRepository.save(conductorDtoToEntity(conductorDto));
            return conductorEntityToDto(updatedConductor);
        } else {
            throw new IllegalArgumentException("Existe una discrepancia entre el ID proporcionado en la URL y el ID del registro correspondiente.");
        }
    }


    @Override
    public void deleteConductor(Integer id) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Conductor","id",id));
        conductorRepository.delete(conductor);
    }

    // Convertir de DTo a Entidad
    public ConductorDto conductorEntityToDto(Conductor conductor){
        return modelMapper.map(conductor, ConductorDto.class);
    }

    public Conductor conductorDtoToEntity(ConductorDto conductorDto){
        return modelMapper.map(conductorDto, Conductor.class);
    }
    public void validarDatos(ConductorDto conductorDto, Integer id ){
        List<Conductor> conductors= conductorRepository.findAll();
        for(Conductor c: conductors){
            if(c.getTrabajador().getId().equals(conductorDto.getTrabajador().getId()) && !c.getId().equals(id)){
                throw new DataIntegrityViolationException("El trabajador ya está asignado a un conductor.");
            }
        }
        if(trabajadorRepository.findById(conductorDto.getTrabajador().getId()).isEmpty()){
            throw new IllegalArgumentException("El id del trabajador no existe en la base de datos.");
        } else if(camionRepository.findById(conductorDto.getCamion().getId()).isEmpty()){
            throw new IllegalArgumentException("El id del camion no existe en la base de datos.");
        }
    }
    public void validarDatos(ConductorDto conductorDto ){
        List<Conductor> conductors= conductorRepository.findAll();
        for(Conductor c: conductors){
            if(c.getTrabajador().getId().equals(conductorDto.getTrabajador().getId())){
                throw new DataIntegrityViolationException("El trabajador ya está asignado a un conductor.");
            }
        }
        if(trabajadorRepository.findById(conductorDto.getTrabajador().getId()).isEmpty()){
            throw new IllegalArgumentException("El id del trabajador no existe en la base de datos.");
        } else if(camionRepository.findById(conductorDto.getCamion().getId()).isEmpty()){
            throw new IllegalArgumentException("El id del camion no existe en la base de datos.");
        }
    }
}










