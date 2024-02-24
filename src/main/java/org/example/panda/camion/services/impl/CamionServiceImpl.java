package org.example.panda.camion.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.dtos.CamionResponse;
import org.example.panda.camion.entity.Camion;
import org.example.panda.camion.repository.CamionRepository;
import org.example.panda.camion.services.ICamionService;
import org.example.panda.carreta.repository.CarretaRepository;
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
    private final CarretaRepository carretaRepository;
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
        Camion existingCamion = camionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camion", "id", id));

        if (camionDto.getId() == null || id.equals(camionDto.getId())) {
            validarPlaca(camionDto, id);

            // Actualiza solo los campos que se pueden modificar
            existingCamion.setMarca(camionDto.getMarca());
            existingCamion.setModelo(camionDto.getModelo());
            existingCamion.setAnoFabricacion(camionDto.getAnoFabricacion());
            existingCamion.setPlaca(camionDto.getPlaca());

            // No actualiza la relación carreta

            return camionEntityToDto(camionRepository.save(existingCamion));
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
                throw new IllegalArgumentException("La placa ingresada ya existe en nuestro sistema.");
            }
        }
        if(carretaRepository.findById(camionDto.getCarreta().getId()).isEmpty()){
            throw new IllegalArgumentException("El id no existe en la base de datos.");
        }
    }
    public void validarPlaca(CamionDto camionDto) {
        List<Camion> camiones = camionRepository.findAll();
        for (Camion c : camiones) {
            // Verifica si la placa es null antes de llamar a getPlaca()
            if (c.getPlaca() != null && c.getPlaca().equals(camionDto.getPlaca())) {
                throw new IllegalArgumentException("La placa ingresada ya existe en nuestro sistema.");
            }
        }

        // Verifica si el objeto camionDto o su propiedad carreta son null antes de intentar acceder a su ID
        if (camionDto.getCarreta() == null || carretaRepository.findById(camionDto.getCarreta().getId()).isEmpty()) {
            throw new IllegalArgumentException("El id de la carreta no existe en la base de datos.");
        }
    }
}


















