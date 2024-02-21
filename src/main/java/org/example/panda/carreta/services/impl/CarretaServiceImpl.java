package org.example.panda.carreta.services.impl;

import lombok.AllArgsConstructor;
import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.entity.Camion;
import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.carreta.dtos.CarretaResponse;
import org.example.panda.carreta.entity.Carreta;
import org.example.panda.carreta.repository.CarretaRepository;
import org.example.panda.carreta.services.ICarretaService;
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
public class CarretaServiceImpl implements ICarretaService {

    private final ModelMapper modelMapper;

    private final CarretaRepository carretaRepository;

    @Override
    public CarretaDto createCarreta(CarretaDto carretaDto) {
        validarPlaca(carretaDto);
        return carretaEntityToDto(carretaRepository.save(carretaDtoToEntity(carretaDto)));
    }

    @Override
    public CarretaResponse listCarreta(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<Carreta> carretas = carretaRepository.findAll(pageable);

        List<Carreta> CarretaList = carretas.getContent();
        List<CarretaDto> contenido = CarretaList.stream().map(this::carretaEntityToDto).collect(Collectors.toList());

        return CarretaResponse.builder()
                .contenido(contenido)
                .numeroPagina(carretas.getNumber())
                .medidaPagina(carretas.getSize())
                .totalElementos(carretas.getTotalElements())
                .totalPaginas(carretas.getTotalPages())
                .ultima(carretas.isLast())
                .build();
    }

    @Override
    public CarretaDto listCarretaById(Integer id) {
        Carreta carreta = carretaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carreta", "id", id));
        return carretaEntityToDto(carreta);
    }

    @Override
    public CarretaDto updateCarreta(Integer id, CarretaDto carretaDto) {
        if (carretaDto.getId() == null || id.equals(carretaDto.getId())) {
            validarPlaca(carretaDto, id);
            Carreta findCarreta = carretaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Carreta", "id", id));
            carretaDto.setId(findCarreta.getId());
            return carretaEntityToDto(carretaRepository.save(carretaDtoToEntity(carretaDto)));
        }
        throw new IllegalArgumentException(
                "Existe una discrepancia entre el ID proporcionado en la URL y el ID del registro correspondiente.");
    }

    @Override
    public void deleteCarreta(Integer id) {
        Carreta carreta = carretaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carreta", "id", id));
        carretaRepository.delete(carreta);
    }

    // Convertir de DTO a entidad
    private CarretaDto carretaEntityToDto(Carreta carreta) {
        return modelMapper.map(carreta, CarretaDto.class);
    }

    private Carreta carretaDtoToEntity(CarretaDto carretaDto) {
        return modelMapper.map(carretaDto, Carreta.class);
    }

    public void validarPlaca(CarretaDto carretaDto, Integer id) {
        List<Carreta> camiones = carretaRepository.findAll();
        for (Carreta c : camiones) {
            if (c.getPlaca().equals(carretaDto.getPlaca()) && !c.getId().equals(id)) {
                throw new IllegalArgumentException(
                        "Lamentamos informarle que la placa ingresada ya está registrada en nuestro sistema. Por favor, verifique y proporcione un número de placa único.");
            }
        }
    }

    public void validarPlaca(CarretaDto carretaDto) {
        List<Carreta> camiones = carretaRepository.findAll();
        for(Carreta c : camiones){
            if(c.getPlaca().equals(carretaDto.getPlaca())) {
                throw new IllegalArgumentException("La placa ingresada ya está registrada.");
            }
        }
    }
}
