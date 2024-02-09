package org.example.panda.trabajador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.*;
import org.example.panda.trabajador.repositories.CargoRepository;
import org.example.panda.trabajador.repositories.EstadoCivilRepository;
import org.example.panda.trabajador.repositories.GeneroRepository;
import org.example.panda.trabajador.repositories.NacionalidadRepository;
import org.example.panda.trabajador.services.ITrabajadorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest //pruebas a los controladores
public class TrabajadorControllerTests {
    @Autowired
    private MockMvc mockMvc; //realizar peticiones http

    @MockBean //reemplaza cualquier bean existente del mismo tipo en el contexto de la aplicación
    private ITrabajadorService trabajadorService;

    @Autowired
    private ObjectMapper objectMapper; //ObjectMapper es una clase proporcionada por la biblioteca Jackson que se utiliza para convertir objetos Java en representaciones JSON y viceversa.

    @MockBean
    private CargoRepository cargoRepository;
    @MockBean
    private EstadoCivilRepository estadoCivilRepository;
    @MockBean
    private GeneroRepository generoRepository;
    @MockBean
    private NacionalidadRepository nacionalidadRepository;

    private final Cargo cargo = new Cargo(1L, Cargo.CargoEnum.Conductor_de_Camion);
    private final EstadoCivil estadoCivil = new EstadoCivil(1L, EstadoCivil.EstadoCivilEnum.Casado);
    private final Genero genero= new Genero(1L, Genero.GeneroEnum.Masculino);
    private final Nacionalidad nacionalidad= new Nacionalidad(1L, Nacionalidad.NacionalidadEnum.Peruana);
    private TrabajadorDto trabajador1;
    private TrabajadorDto trabajador2;
    private TrabajadorDto trabajadorActualizado;
    private Date fechaNacimiento;
    private Date fechaIngreso;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


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
        Timestamp timestampFechaNacimiento = new Timestamp(fechaNacimiento.getTime());
        Timestamp timestampFechaIngreso = new Timestamp(fechaIngreso.getTime());
        trabajador1 = TrabajadorDto.builder()
                .id(1)
                .nombres("Rodrigo James")
                .apellidos("Ganto Manzanedo")
                .numIdentidad("987654321")
                .fechaNacimiento(timestampFechaNacimiento)
                .genero(genero1)
                .estadoCivil(estadoCivil1)
                .nacionalidad(nacionalidad1)
                .direccionResidencia("Callao")
                .telefono("987654321")
                .email("pantajefferson173@gmail.com")
                .cargo(cargo1)
                .fechaIngreso(timestampFechaIngreso)
                .numCuentaBancaria("12345678912345")
                .build();
        trabajador2 = TrabajadorDto.builder()
                .id(2)
                .nombres("Carlos")
                .apellidos("Gonzales")
                .numIdentidad("73005607")
                .fechaNacimiento(timestampFechaNacimiento)
                .genero(genero1)
                .estadoCivil(estadoCivil1)
                .nacionalidad(nacionalidad1)
                .direccionResidencia("Tumbes")
                .telefono("30889076")
                .email("carlos@gmail.com")
                .cargo(cargo1)
                .fechaIngreso(timestampFechaIngreso)
                .numCuentaBancaria("12345678912311")
                .build();
        trabajadorActualizado = TrabajadorDto.builder()
                .nombres("Carlos")
                .apellidos("Gonzales")
                .numIdentidad("73005607")
                .fechaNacimiento(timestampFechaNacimiento)
                .genero(genero1)
                .estadoCivil(estadoCivil1)
                .nacionalidad(nacionalidad1)
                .direccionResidencia("Tumbes")
                .telefono("30889076")
                .email("carlos@gmail.com")
                .cargo(cargo1)
                .fechaIngreso(timestampFechaIngreso)
                .numCuentaBancaria("12345678912311")
                .build();
    }
    @Test
    @DisplayName("Test para guardar un Trabajador")
    @Order(1)
    void testGuardarTrabajador() throws Exception {
        //given //dado a que guardamos algún trabajador
        given(trabajadorService.createTrabajador(any(TrabajadorDto.class))) //any(Trabajador.class) significa que se espera que el método createTrabajador sea llamado con cualquier instancia de la clase TrabajadorDto.
                .willAnswer((invocation) ->invocation.getArgument(0)); //obtenemos el argumento numero 0 //En este caso, el lambda devuelve el primer argumento que se pasó al método mockeado (invocation.getArgument(0)).
        //Entonces, en términos más sencillos, este fragmento de código indica que cuando el método createTrabajador del servicio createTrabajador sea invocado durante la prueba, en lugar de realizar alguna lógica real, simplemente devolverá el mismo objeto Empleado que se le pasó como argumento.
        //when/ cuando hagamos una peticion post, esperamos que el tipo del contenido sea json
        ResultActions response = mockMvc.perform(post("/api/v1/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trabajador1))); //convertimos a formato json

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombres",is(trabajador1.getNombres())))
                .andExpect(jsonPath("$.apellidos",is(trabajador1.getApellidos())))
                .andExpect(jsonPath("$.numIdentidad",is(trabajador1.getNumIdentidad())))
                .andExpect(jsonPath("$.fechaNacimiento",is("2003-03-20T05:00:00.000+00:00")))
                .andExpect(jsonPath("$.genero.nombreGenero",is("Masculino")))
                .andExpect(jsonPath("$.estadoCivil.nombreEstadoCivil",is("Casado")))
                .andExpect(jsonPath("$.nacionalidad.nombreNacionalidad",is("Peruana")))
                .andExpect(jsonPath("$.direccionResidencia",is(trabajador1.getDireccionResidencia())))
                .andExpect(jsonPath("$.telefono",is(trabajador1.getTelefono())))
                .andExpect(jsonPath("$.email",is(trabajador1.getEmail())))
                .andExpect(jsonPath("$.cargo.nombreCargo",is("Conductor_de_Camion")))
                .andExpect(jsonPath("$.fechaIngreso",is("2014-01-01T05:00:00.000+00:00")))
                .andExpect(jsonPath("$.numCuentaBancaria",is(trabajador1.getNumCuentaBancaria())));
    }
    @Test
    @DisplayName("Test para listar Trabajadores")
    @Order(2)
    void testListarTrabajador() throws Exception {
        //given
        List<TrabajadorDto> listaTrabajadores = new ArrayList<>();
        listaTrabajadores.add(trabajador1);
        listaTrabajadores.add(trabajador2);
        TrabajadorResponse trabajadorResponse= new TrabajadorResponse(listaTrabajadores,0,10,2,1, true);
        given(trabajadorService.listTrabajadores(0,10,"id","0")).willReturn(trabajadorResponse);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/trabajadores"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.contenido.size()",is(trabajadorResponse.getContenido().size())));
    }
    @Test
    @DisplayName("Test para listar Trabajador por ID")
    @Order(3)
    void testObtenerTrabajadorPorId() throws Exception {
        //given
        given(trabajadorService.listTrabajadorById(trabajador1.getId())).willReturn(trabajador1);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/trabajador/{id}",trabajador1.getId()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombres",is(trabajador1.getNombres())))
                .andExpect(jsonPath("$.apellidos",is(trabajador1.getApellidos())))
                .andExpect(jsonPath("$.numIdentidad",is(trabajador1.getNumIdentidad())))
                .andExpect(jsonPath("$.fechaNacimiento",is("2003-03-20T05:00:00.000+00:00")))
                .andExpect(jsonPath("$.genero.nombreGenero",is("Masculino")))
                .andExpect(jsonPath("$.estadoCivil.nombreEstadoCivil",is("Casado")))
                .andExpect(jsonPath("$.nacionalidad.nombreNacionalidad",is("Peruana")))
                .andExpect(jsonPath("$.direccionResidencia",is(trabajador1.getDireccionResidencia())))
                .andExpect(jsonPath("$.telefono",is(trabajador1.getTelefono())))
                .andExpect(jsonPath("$.email",is(trabajador1.getEmail())))
                .andExpect(jsonPath("$.cargo.nombreCargo",is("Conductor_de_Camion")))
                .andExpect(jsonPath("$.fechaIngreso",is("2014-01-01T05:00:00.000+00:00")))
                .andExpect(jsonPath("$.numCuentaBancaria",is(trabajador1.getNumCuentaBancaria())));
    }
    @Test
    @DisplayName("Test para listar Trabajador vacio")
    @Order(4)
    void testObtenerTrabajadorNoEncontrado() throws Exception {
        //given

        given(trabajadorService.listTrabajadorById(trabajador1.getId())).willThrow(new ResourceNotFoundException("Trabajador", "id", trabajador1.getId()));;

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/trabajador/{id}",trabajador1.getId()));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    @DisplayName("Test para actualizar Trabajador")
    @Order(5)
    void testActualizarTrabajador() throws Exception {
        given(trabajadorService.updateTrabajador(eq(trabajador1.getId()),any(TrabajadorDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(1));

        //when
        ResultActions response = mockMvc.perform(put("/api/v1/trabajador/{id}",trabajador1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trabajadorActualizado)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombres",is(trabajadorActualizado.getNombres())))
                .andExpect(jsonPath("$.apellidos",is(trabajadorActualizado.getApellidos())))
                .andExpect(jsonPath("$.numIdentidad",is(trabajadorActualizado.getNumIdentidad())))
                .andExpect(jsonPath("$.fechaNacimiento",is("2003-03-20T05:00:00.000+00:00")))
                .andExpect(jsonPath("$.genero.nombreGenero",is("Masculino")))
                .andExpect(jsonPath("$.estadoCivil.nombreEstadoCivil",is("Casado")))
                .andExpect(jsonPath("$.nacionalidad.nombreNacionalidad",is("Peruana")))
                .andExpect(jsonPath("$.direccionResidencia",is(trabajadorActualizado.getDireccionResidencia())))
                .andExpect(jsonPath("$.telefono",is(trabajadorActualizado.getTelefono())))
                .andExpect(jsonPath("$.email",is(trabajadorActualizado.getEmail())))
                .andExpect(jsonPath("$.cargo.nombreCargo",is("Conductor_de_Camion")))
                .andExpect(jsonPath("$.fechaIngreso",is("2014-01-01T05:00:00.000+00:00")))
                .andExpect(jsonPath("$.numCuentaBancaria",is(trabajadorActualizado.getNumCuentaBancaria())));
    }

    @Test
    @DisplayName("Test para actualizar Trabajador no encontrado")
    @Order(6)
    void testActualizarTrabajadorNoEncontrado() throws Exception {
        given(trabajadorService.updateTrabajador(eq(trabajador1.getId()),any(TrabajadorDto.class)))
                .willThrow(new ResourceNotFoundException("Trabajador", "id", trabajador1.getId() ));

        //when
        ResultActions response = mockMvc.perform(put("/api/v1/trabajador/{id}",trabajador1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trabajadorActualizado)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    @DisplayName("Test para eliminar trabajador")
    @Order(7)
    void testEliminarTrabajador() throws Exception {
        willDoNothing().given(trabajadorService).deleteTrabajador(trabajador1.getId());

        //when
        ResultActions response = mockMvc.perform(delete("/api/v1/trabajador/{id}",trabajador1.getId()));

        //then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
