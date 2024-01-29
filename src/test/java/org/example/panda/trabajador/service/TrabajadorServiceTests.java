package org.example.panda.trabajador.service;

//para seguir trabajando con BDD importamos

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.*;
import org.example.panda.trabajador.repositories.*;
import org.example.panda.trabajador.services.impl.TrabajadorServiceImpl;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ExtendWith(MockitoExtension.class) //Esta notacion sirve para indicarle que trabajaremos con mockito y cargar extenciones de mockito en la que usa extenciones de junit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrabajadorServiceTests {
    @Mock //Mock crea un simulacro de estos repositorios
    private TrabajadorRepository trabajadorRepository;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private EstadoCivilRepository estadoCivilRepository;
    @Mock
    private GeneroRepository generoRepository;
    @Mock
    private NacionalidadRepository nacionalidadRepository;

    @InjectMocks //inyectamos empleadosRepository dentro de empleadoService
    private TrabajadorServiceImpl trabajadorService;

    private final Cargo cargo = new Cargo(1L, Cargo.CargoEnum.Conductor_de_Camion);
    private final EstadoCivil estadoCivil = new EstadoCivil(1L, EstadoCivil.EstadoCivilEnum.Casado);
    private final Genero genero= new Genero(1L, Genero.GeneroEnum.Masculino);
    private final Nacionalidad nacionalidad= new Nacionalidad(1L, Nacionalidad.NacionalidadEnum.Peruana);
    private Trabajador trabajador1;
    private Trabajador trabajador2;
    private Date fechaNacimiento;
    private Date fechaIngreso;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        trabajadorService = new TrabajadorServiceImpl(modelMapper, trabajadorRepository);
    }

    @BeforeEach
    void guardarTrabajador() throws ParseException {
        given(generoRepository.findById(1L)).willReturn(Optional.of(genero));
        given(cargoRepository.findById(1L)).willReturn(Optional.of(cargo));
        given(estadoCivilRepository.findById(1L)).willReturn(Optional.of(estadoCivil));
        given(nacionalidadRepository.findById(1L)).willReturn(Optional.of(nacionalidad));

        Genero genero1 = generoRepository.findById(1L).orElseThrow();  // Uso de orElseThrow para lanzar una excepción si el Optional está vacío
        Cargo cargo1 = cargoRepository.findById(1L).orElseThrow();
        EstadoCivil estadoCivil1 = estadoCivilRepository.findById(1L).orElseThrow();
        Nacionalidad nacionalidad1 = nacionalidadRepository.findById(1L).orElseThrow();
        fechaNacimiento = sdf.parse("2003-03-20");
        fechaIngreso=sdf.parse("2014-01-01");
        trabajador1 = Trabajador.builder()
                .id(1)
                .nombres("Rodrigo James")
                .apellidos("Ganto Manzanedo")
                .numIdentidad("987654321")
                .fechaNacimiento(fechaNacimiento)
                .genero(genero1)
                .estadoCivil(estadoCivil1)
                .nacionalidad(nacionalidad1)
                .direccionResidencia("Callao")
                .telefono("987654321")
                .email("pantajefferson173@gmail.com")
                .cargo(cargo1)
                .fechaIngreso(fechaIngreso)
                .numCuentaBancaria("12345678912345")
                .build();
        trabajador2 = Trabajador.builder()
                .id(2)
                .nombres("Carlos")
                .apellidos("Gonzales")
                .numIdentidad("73005607")
                .fechaNacimiento(fechaNacimiento)
                .genero(genero1)
                .estadoCivil(estadoCivil1)
                .nacionalidad(nacionalidad1)
                .direccionResidencia("Tumbes")
                .telefono("30889076")
                .email("carlos@gmail.com")
                .cargo(cargo1)
                .fechaIngreso(fechaIngreso)
                .numCuentaBancaria("12345678912311")
                .build();
    }

    @DisplayName("Test para guardar un Trabajador")
    @Order(1)
    @Test
    void testGuardarTrabajador(){
        //given

        given(trabajadorRepository.save(trabajador1)).willReturn(trabajador1);
        given(modelMapper.map(any(), eq(Trabajador.class))).willReturn(trabajador1);
        given(modelMapper.map(any(), eq(TrabajadorDto.class))).willReturn(trabajadorEntityToDto(trabajador1));
        //when
        TrabajadorDto trabajadorGuardado = trabajadorService.createTrabajador(trabajadorEntityToDto(trabajador1));
        //then
        assertThat(trabajadorGuardado).isNotNull();
        assertThat(trabajadorGuardado.getId()).isEqualTo(1);
    }
    @DisplayName("Test para listar trabajadores vacio")
    @Order(2)
    @Test
    void testListarTrabajadoresVacio(){
        //given
        int numeroDePagina=0, medidaDePagina=10;
        String ordenarPor= "id",sortDir="0";
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();//le indicamos que si pasa un parametro sera de forma ascendente, de lo contraio será de forma descendente.

        Pageable pageable= PageRequest.of(numeroDePagina,medidaDePagina, sort);
        given(trabajadorRepository.findAll(pageable)).willReturn(Page.empty());
        //when
        TrabajadorResponse  trabajadorResponse = trabajadorService.listTrabajadores(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        //then
        assertThat(trabajadorResponse.getContenido()).isEmpty();
        assertThat(trabajadorResponse.getTotalElementos()).isEqualTo(0);

    }
    @DisplayName("Test para listar trabajadores")
    @Order(3)
    @Test
    void testListarTrabajadores(){
        //given
        int numeroDePagina=0, medidaDePagina=10;
        String ordenarPor= "id",sortDir="0";
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();//le indicamos que si pasa un parametro sera de forma ascendente, de lo contraio será de forma descendente.

        Pageable pageable= PageRequest.of(numeroDePagina,medidaDePagina, sort);
        given(trabajadorRepository.findAll(pageable)).willReturn(new PageImpl<>(Arrays.asList(trabajador1, trabajador2)));
        //when
        TrabajadorResponse  trabajadorResponse = trabajadorService.listTrabajadores(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        //then
        assertThat(trabajadorResponse.getContenido()).hasSize(2); // Verifica que haya dos registros
        assertThat(trabajadorResponse.getTotalElementos()).isEqualTo(2);

    }
    @DisplayName("Test para listar trabajador por ID VACIO")
    @Order(4)
    @Test
    void listarTrabajadorByIdVacio(){
        int id=1;
        //GIVEN
        given(trabajadorRepository.findById(id)).willReturn(Optional.empty());
        //WHEN
        Throwable exception = assertThrows(ResourceNotFoundException.class,
                () -> trabajadorService.listTrabajadorById(id));
        //THEN
        assertThat(exception.getMessage()).isEqualTo("Trabajador no encontrado/a con : id : '"+id+"'");
    }
    @DisplayName("Test para listar trabajador por ID ")
    @Order(5)
    @Test
    void listarTrabajadorById(){
        //GIVEN
        given(trabajadorRepository.findById(trabajador1.getId())).willReturn(Optional.of(trabajador1));
        given(modelMapper.map(any(), eq(TrabajadorDto.class))).willReturn(trabajadorEntityToDto(trabajador1));
        //WHEN
        TrabajadorDto trabajadorDto= trabajadorService.listTrabajadorById(trabajador1.getId());
        //THEN

        assertThat(trabajadorDto).isNotNull();
        assertThat(trabajadorDto.getId()).isEqualTo(1);

    }
    @DisplayName("Test para actualizar trabajador")
    @Order(6)
    @Test
    void actualizarTrabajador(){
        // Given

        given(trabajadorRepository.findById(trabajador1.getId())).willReturn(Optional.of(trabajador1));

        trabajador1.setNombres("Nombre Actualizado");
        trabajador1.setEmail("emailActualizado@gmail.com");
        trabajador1.setNumCuentaBancaria("12345678912111");

        given(trabajadorRepository.save(any(Trabajador.class))).willReturn(trabajador1);
        // Simplemente devuelve el mismo objeto
        given(modelMapper.map(any(), eq(Trabajador.class))).willReturn(trabajador1);
        given(modelMapper.map(any(), eq(TrabajadorDto.class))).willReturn(trabajadorEntityToDto(trabajador1));
        // When

        TrabajadorDto trabajadorActualizado = trabajadorService.updateTrabajador(trabajador1.getId(), trabajadorEntityToDto(trabajador1));

        // Then
        assertThat(trabajadorActualizado).isNotNull();
        assertThat(trabajadorActualizado.getId()).isEqualTo(1);
        assertThat(trabajadorActualizado.getEmail()).isEqualTo("emailActualizado@gmail.com");
        assertThat(trabajadorActualizado.getNombres()).isEqualTo("Nombre Actualizado");
        assertThat(trabajadorActualizado.getNumCuentaBancaria()).isEqualTo("12345678912111");
    }
    @DisplayName("Test para eliminar trabajador")
    @Order(7)
    @Test
    void testEliminarTrabajador(){
        //given
        int id=1;

        // Configura el repositorio para devolver un trabajador cuando se llama a findById
        given(trabajadorRepository.findById(id)).willReturn(Optional.of(trabajador1));

        // when
        trabajadorService.deleteTrabajador(id);

        // then
        // Verifica que trabajadorRepository.findById se llame una vez con el ID proporcionado
        verify(trabajadorRepository, times(1)).findById(id);

        // Verifica que trabajadorRepository.delete se llame una vez con el trabajador recuperado
        verify(trabajadorRepository, times(1)).delete(trabajador1);
    }


    private TrabajadorDto trabajadorEntityToDto(Trabajador trabajador){
        return TrabajadorDto.builder()
                .id(trabajador.getId())
                .nombres(trabajador.getNombres())
                .apellidos(trabajador.getApellidos())
                .numIdentidad(trabajador.getNumIdentidad())
                .fechaNacimiento(trabajador.getFechaNacimiento())
                .genero(trabajador.getGenero())
                .estadoCivil(trabajador.getEstadoCivil())
                .nacionalidad(trabajador.getNacionalidad())
                .direccionResidencia(trabajador.getDireccionResidencia())
                .telefono(trabajador.getTelefono())
                .email(trabajador.getEmail())
                .cargo(trabajador.getCargo())
                .fechaIngreso(trabajador.getFechaIngreso())
                .numCuentaBancaria(trabajador.getNumCuentaBancaria())
                .build();
    }

}
