package org.example.panda.trabajador.services.impl;

import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.Trabajador;
import org.example.panda.trabajador.repositories.TrabajadorRepository;
import org.example.panda.trabajador.services.ITrabajadorService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService {
    private final ModelMapper modelMapper;
    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorServiceImpl(ModelMapper modelMapper, TrabajadorRepository trabajadorRepository) {
        this.modelMapper = modelMapper;
        this.trabajadorRepository = trabajadorRepository;
    }


    @Override
    public TrabajadorDto createTrabajador(TrabajadorDto trabajadorDto) {

        return entityToDto(trabajadorRepository.save(dtoToEntity(trabajadorDto)));
    }

    @Override
    public TrabajadorResponse listTrabajadores(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();//le indicamos que si pasa un parametro sera de forma ascendente, de lo contraio será de forma descendente.

        Pageable pageable= PageRequest.of(numeroDePagina,medidaDePagina, sort);
        Page<Trabajador> trabajadores= trabajadorRepository.findAll(pageable);

        List<Trabajador> TrabajadoresList= trabajadores.getContent();
        List<TrabajadorDto> contenido= TrabajadoresList.stream().map(this::entityToDto).collect(Collectors.toList()); //convertimos a dto
        return TrabajadorResponse.builder()
                .contenido(contenido)
                .numeroPagina(trabajadores.getNumber())
                .medidaPagina(trabajadores.getSize())
                .totalElementos(trabajadores.getTotalElements())
                .totalPaginas(trabajadores.getTotalPages())
                .ultima(trabajadores.isLast()) //confirma si es la última página
                .build();
    }

    @Override
    public Optional<TrabajadorDto> listTrabajadorById(Integer id) {
        Optional<Trabajador> trabajador=trabajadorRepository.findById(id);
       return trabajador.isPresent() ?  trabajador.map(this::entityToDto): Optional.empty();

    }

    @Override
    public Optional<TrabajadorDto> updateTrabajador(Integer id, TrabajadorDto trabajadorDto) {
        Optional<Trabajador> findTrabajador= trabajadorRepository.findById(id);
        if(findTrabajador.isPresent()){
            Trabajador existentTrabajador = findTrabajador.get();
            existentTrabajador.builder()
                    .nombres(trabajadorDto.getNombres())
                    .apellidos(trabajadorDto.getApellidos())
                    .numIdentidad(trabajadorDto.getNumIdentidad())
                    .fechaNacimiento(trabajadorDto.getFechaNacimiento())
                    .genero(trabajadorDto.getGenero())
                    .estadoCivil(trabajadorDto.getEstadoCivil())
                    .nacionalidad(trabajadorDto.getNacionalidad())
                    .direccionResidencia(trabajadorDto.getDireccionResidencia())
                    .telefono(trabajadorDto.getTelefono())
                    .email(trabajadorDto.getEmail())
                    .cargo(trabajadorDto.getCargo())
                    .fechaIngreso(trabajadorDto.getFechaIngreso())
                    .numCuentaBancaria(trabajadorDto.getNumCuentaBancaria())
                    .build();
            trabajadorRepository.save(existentTrabajador);
            return Optional.of(entityToDto(existentTrabajador));
        }
        return Optional.empty();

    }

    @Override
    public boolean deleteTrabajadorById(Integer Id) {
        return trabajadorRepository.findById(Id).isPresent();
    }
    /*AQUI HACEMOS USO DE ModelMapper PARA AHORRAR MUCHAS LINEAS DE CODIGO, PERO DEJAREMOS COMENTADO LO QUE HABIAMOS HECHO ANTES A MODO DE PRÁCTICA*/
    private TrabajadorDto entityToDto(Trabajador trabajador){
        return modelMapper.map(trabajador, TrabajadorDto.class);//PublicacionDTO.class: La clase destino a la que se mapearán los datos.
    }
    //convertir de DTO a Entidad
    private Trabajador dtoToEntity(TrabajadorDto trabajadorDto){
        return modelMapper.map(trabajadorDto, Trabajador.class);
    }
}
