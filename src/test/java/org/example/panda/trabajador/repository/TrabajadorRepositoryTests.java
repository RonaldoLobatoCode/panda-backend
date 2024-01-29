package org.example.panda.trabajador.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.example.panda.trabajador.entities.*;
import org.example.panda.trabajador.repositories.*;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@DataJpaTest//indicamos que en esta clase solo podemos hacer tests o pruebas solo a la capa de repositorio jpa/no prueba ni controladores ni services, solo prueba entidades y repositorios
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrabajadorRepositoryTests {
    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    private final Cargo cargo = new Cargo(1L, Cargo.CargoEnum.Conductor_de_Camion);
    private final EstadoCivil estadoCivil = new EstadoCivil(1L, EstadoCivil.EstadoCivilEnum.Casado);
    private final Genero genero= new Genero(1L, Genero.GeneroEnum.Masculino);
    private final Nacionalidad nacionalidad= new Nacionalidad(1L, Nacionalidad.NacionalidadEnum.Peruana);
    private Trabajador trabajador1;
    private Trabajador trabajador2;
    private Date fechaNacimiento;
    private Date fechaIngreso;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void guardarTrabajador() throws ParseException {

        Genero genero1 = generoRepository.save(genero);
        Cargo cargo1 = cargoRepository.save(cargo);
        EstadoCivil estadoCivil1= estadoCivilRepository.save(estadoCivil);
        Nacionalidad nacionalidad1= nacionalidadRepository.save(nacionalidad);
        fechaNacimiento = sdf.parse("2003-03-20");
        fechaIngreso=sdf.parse("2014-01-01");
        trabajador1 = Trabajador.builder()
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
    @DisplayName("Test para guardar un trabajador")
    @Test
    @Order(1)
    void testGuardarTrabajador() throws ParseException {
        //metodologia BDD
        //given - dado o condición previa a configuración - Dado este trabajador

           //----este paso lo tenemos en el before.

        //when - acción o el comportamiento que vamos a probar - cuando llamemos al repositorio y guardemos al trabajador
        Trabajador trabajadorGuardado= trabajadorRepository.save(trabajador1);
        //then - verificar la salida -
        assertThat(trabajadorGuardado).isNotNull();
        assertThat(trabajadorGuardado.getId()).isEqualTo(1);//esperamos que no sea nulo y su id sea mayor a 0

        System.out.println(trabajadorGuardado.getId());
        //todo se lee: dado el trabajador cuando lo guardemos esperamos que no sea nullo y que su id sea igual a 0
    }

    @DisplayName("Test para Listar todos los trabajadores.")
    @Test
    @Order(2)
    void testListarTrabajadores(){
        //BDD
        //given

        trabajadorRepository.save(trabajador1);
        trabajadorRepository.save(trabajador2);
        //when
        List<Trabajador> trabajadorList= trabajadorRepository.findAll();
        //then
        assertThat(trabajadorList.size()).isEqualTo(2);
        assertThat(trabajadorList).isNotNull();
    }

    @DisplayName("Test para un trabajador por id")
    @Test
    @Order(3)
    void testListarTravajadorById(){
        //BDD

        //GIVEN
        trabajadorRepository.save(trabajador1);

        //WHEN
        Optional<Trabajador> trabajadorById= trabajadorRepository.findById(4);
        Optional<Trabajador> trabajadorById2= trabajadorRepository.findById(5);

        //THEN
        assertThat(trabajadorById).isNotEmpty();
        assertThat(trabajadorById.get().getId()).isEqualTo(4);
        assertThat(trabajadorById2).isEmpty();
    }
    @DisplayName("Test para actualizar un trabajador")
    @Test
    @Order(4)
    void testActualizarTrabajador(){
        //BDD
        //GIVEN
        trabajadorRepository.save(trabajador1);
        Trabajador trabajadorGuardado= trabajadorRepository.findById(5).get();

        //al ejecutar la prueba completa, cada que una prueba guarda un trabajador, esta le guarda por toda la
        // ejecucion su espacio de memoria y su id por lo que los ids se van incrementando pero estos registros
        // antetiores no existen en las pruebas posteriores por lo que no nos sale error cuando registramos los
        // mismos objetos que al inicio ya que nosotros pusimos que algunos campos sean únicos.
        //WHEN
        trabajadorGuardado.setNombres("Piero");
        trabajadorGuardado.setApellidos("Andrade");
        trabajadorGuardado.setEmail("pieroAndrade@gmail.com");
        Trabajador trabajadorActualizado= trabajadorRepository.save(trabajadorGuardado);
        //THEN
        assertThat(trabajadorActualizado).isNotNull();
        assertThat(trabajadorActualizado.getId()).isEqualTo(5);
        assertThat(trabajadorActualizado.getNombres()).isEqualTo("Piero");
        assertThat(trabajadorActualizado.getApellidos()).isEqualTo("Andrade");
        assertThat(trabajadorActualizado.getEmail()).isEqualTo("pieroAndrade@gmail.com");
    }
    @DisplayName("Test para eliminar un trabajador")
    @Test
    @Order(5)
    void testEliminarTrabajador(){
        //BDD
        //GIVEN
        Trabajador trabajador = trabajadorRepository.save(trabajador1);
        //WHEN
        trabajadorRepository.delete(trabajador);
        List<Trabajador> trabajadores=trabajadorRepository.findAll();
        //THEN
        assertThat(trabajadores.size()).isEqualTo(0);
    }
}
