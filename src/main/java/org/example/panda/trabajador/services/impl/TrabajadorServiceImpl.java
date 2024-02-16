package org.example.panda.trabajador.services.impl;
import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.feignClient.ReniecClient;
import org.example.panda.feignClient.response.ReniecResponse;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.Trabajador;
import org.example.panda.trabajador.repositories.TrabajadorRepository;
import org.example.panda.trabajador.services.ITrabajadorService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService {

    private final ModelMapper modelMapper;
    private final TrabajadorRepository trabajadorRepository;
    private final ReniecClient reniecClient;
    public TrabajadorServiceImpl(ModelMapper modelMapper, TrabajadorRepository trabajadorRepository, ReniecClient reniecClient) {
        this.modelMapper = modelMapper;
        this.trabajadorRepository = trabajadorRepository;
        this.reniecClient = reniecClient;
    }
    @Value("${token.api}")
    private String tokenApi;
    @Transactional
    @Override
    public TrabajadorDto createTrabajador(TrabajadorDto trabajadorDto) {
        validateUniqueValues(trabajadorDto);
        return trabajadorEntityToDto(trabajadorRepository.save(trabajadorDtoToEntity(trabajadorDto)));
    }
    @Transactional(readOnly = true) //usamos esto para indicarle que haga los procesos de transacción automáticos, como begin, commit, rollback, etc. y nosotros ya no preocuparnos por ello
    //generalmente usamos @Transactional(readOnly=true) para la operación de búsqueda o recuperación para asegurarnos de que solo
    //podamos realizar la operacion de solo lectura
    @Override
    public TrabajadorResponse listTrabajadores(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();//le indicamos que si pasa un parametro sera de forma ascendente, de lo contraio será de forma descendente.

        Pageable pageable= PageRequest.of(numeroDePagina,medidaDePagina, sort);
        Page<Trabajador> trabajadores= trabajadorRepository.findAll(pageable);

        List<Trabajador> TrabajadoresList= trabajadores.getContent();
        List<TrabajadorDto> contenido= TrabajadoresList.stream().map(this::trabajadorEntityToDto).collect(Collectors.toList()); //convertimos a dto
        return TrabajadorResponse.builder()
                .contenido(contenido)
                .numeroPagina(trabajadores.getNumber())
                .medidaPagina(trabajadores.getSize())
                .totalElementos(trabajadores.getTotalElements())
                .totalPaginas(trabajadores.getTotalPages())
                .ultima(trabajadores.isLast()) //confirma si es la última página
                .build();
    }
    @Transactional(readOnly = true)
    @Override
    public TrabajadorDto listTrabajadorById(Integer id) {
        Trabajador trabajador=trabajadorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Trabajador", "id", id));
       return trabajadorEntityToDto(trabajador);
    }
    @Override
    public ReniecResponse getInfoReniec(String numero) {
        ReniecResponse response=getExecution(numero);
        if(response != null){
            return response;
        } else {
            throw new IllegalArgumentException("el dni no está disponible!");
        }
    }


    @Transactional
    @Override
    public TrabajadorDto updateTrabajador(Integer id, TrabajadorDto trabajadorDto) {
        if(trabajadorDto.getId()==null || id.equals(trabajadorDto.getId())){
            validateUniqueValues(trabajadorDto, id);
            Trabajador findTrabajador= trabajadorRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Trabajador", "id", id));
            trabajadorDto.setId(findTrabajador.getId());
            return trabajadorEntityToDto(trabajadorRepository.save(trabajadorDtoToEntity(trabajadorDto)));
        }
            throw new IllegalArgumentException("El ID enviado por la URL es distinto al del registro.");
    }
    @Transactional
    @Override
    public void deleteTrabajador(Integer Id) {
        Trabajador trabajador=trabajadorRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Trabajador", "id", Id));
        trabajadorRepository.delete(trabajador);
    }
    public ReniecResponse getExecution(String numero){
        String authorization = "Bearer " + tokenApi;
        return reniecClient.getInfo(numero, authorization);
    }
    /*AQUI HACEMOS USO DE ModelMapper PARA AHORRAR MUCHAS LINEAS DE CODIGO, PERO DEJAREMOS COMENTADO LO QUE HABIAMOS HECHO ANTES A MODO DE PRÁCTICA*/
    private TrabajadorDto trabajadorEntityToDto(Trabajador trabajador){
        return modelMapper.map(trabajador, TrabajadorDto.class);//PublicacionDTO.class: La clase destino a la que se mapearán los datos.
    }
    //convertir de DTO a Entidad
    private Trabajador trabajadorDtoToEntity(TrabajadorDto trabajadorDto){
        return modelMapper.map(trabajadorDto, Trabajador.class);
    }
    public void validateUniqueValues( TrabajadorDto trabajadorDto, Integer id){
        List<Trabajador> trabajadores= trabajadorRepository.findAll();
        for (Trabajador tr : trabajadores) {
            if(tr.getEmail().equals(trabajadorDto.getEmail()) && !tr.getId().equals(id)){
                throw new IllegalArgumentException("El email ya existe, por favor ingrese otro.");

            }else if(tr.getNumIdentidad().equals(trabajadorDto.getNumIdentidad()) && !tr.getId().equals(id)){
                throw new IllegalArgumentException("El número de identidad ya existe, por favor ingrese otro.");
            } else if(tr.getNumCuentaBancaria().equals(trabajadorDto.getNumCuentaBancaria()) && !tr.getId().equals(id)) throw new IllegalArgumentException("El número de de cuenta bancaria ya existe, por favor ingrese otro.");
        }
    }
    public void validateUniqueValues( TrabajadorDto trabajadorDto){
        List<Trabajador> trabajadores= trabajadorRepository.findAll();
        for (Trabajador tr : trabajadores) {
            if(tr.getEmail().equals(trabajadorDto.getEmail())){
                throw new IllegalArgumentException("El email ya existe, por favor ingrese otro.");

            }else if(tr.getNumIdentidad().equals(trabajadorDto.getNumIdentidad())){
                throw new IllegalArgumentException("El número de identidad ya existe, por favor ingrese otro.");
            } else if(tr.getNumCuentaBancaria().equals(trabajadorDto.getNumCuentaBancaria())) throw new IllegalArgumentException("El número de de cuenta bancaria ya existe, por favor ingrese otro.");
        }
    }
}
