package org.example.panda.camion.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.dtos.CamionResponse;
import org.example.panda.camion.entity.Camion;
import org.example.panda.camion.repository.CamionRepository;
import org.example.panda.camion.services.ICamionService;
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
public class CamionServiceImpl implements ICamionService {

    private final ModelMapper modelMapper;

    private final CamionRepository camionRepository;

    @Transactional
    @Override
    public CamionDto createCamion(CamionDto camionDto) {
        validarPlaca(camionDto);
        return camionEntityToDto(camionRepository.save(camionDtoToEntity(camionDto)));
    }

    @Override
    public CamionResponse listCamion(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<Camion> camiones = camionRepository.findAll(pageable);

        List<Camion> CamionesList = camiones.getContent();
        List<CamionDto> contenido = CamionesList.stream().map(this::camionEntityToDto).collect(Collectors.toList());

        return CamionResponse.builder()
                .contenido(contenido)
                .numeroPagina(camiones.getNumber())
                .medidaPagina(camiones.getSize())
                .totalElementos(camiones.getTotalElements())
                .totalPaginas(camiones.getTotalPages())
                .ultima(camiones.isLast())
                .build();
    }

    @Transactional
    @Override
    public CamionDto listCamionById(Integer id) {
        Camion camion = camionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Camion","id",id));
        return camionEntityToDto(camion);
    }
    @Transactional
    @Override
    public CamionDto updateCamion(Integer id, CamionDto camionDto) {
        if (camionDto.getId() == null || id.equals(camionDto.getId())){
            validarPlaca(camionDto, id);
            Camion findCamion = camionRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Camion","id",id));
            camionDto.setId(findCamion.getId());
            return camionEntityToDto(camionRepository.save(camionDtoToEntity(camionDto)));
        }
        throw new IllegalArgumentException("El ID proporcionado vía URL no coincide con nuestros registros.");
    }

    @Transactional
    @Override
    public void deleteCamion(Integer id) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Camion","id",id));
        camionRepository.delete(camion);
    }

    // Convertir de DTO a entidad
    private CamionDto camionEntityToDto(Camion camion){
        return modelMapper.map(camion, CamionDto.class);
    }

    private Camion camionDtoToEntity(CamionDto camionDto){
        return modelMapper.map(camionDto, Camion.class);
    }

    public void validarPlaca(CamionDto camionDto, Integer id){
        List<Camion> camiones = camionRepository.findAll();
        for(Camion c : camiones){
            if(c.getPlaca().equals(camionDto.getPlaca()) && !c.getId().equals(id)) {
                throw new IllegalArgumentException("Lamentamos informarle que la placa ingresada ya está registrada en nuestro sistema. Por favor, verifique y proporcione un número de placa único.");
            }
        }
    }
    public void validarPlaca(CamionDto camionDto){
        List<Camion> camiones = camionRepository.findAll();
        for(Camion c : camiones){
            if(c.getPlaca().equals(camionDto.getPlaca())) {
                throw new IllegalArgumentException("Lamentamos informarle que la placa ingresada ya está registrada en nuestro sistema. Por favor, verifique y proporcione un número de placa único.");
            }
        }
    }
}


















