package org.example.panda.trabajador.repositories;

import org.example.panda.trabajador.entities.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
    @Query(value = "SELECT *FROM trabajadores WHERE num_identidad = :numIdentidad", nativeQuery = true)
    Optional<Trabajador> getTrabajadorByNumIdentidad(String numIdentidad);
}
