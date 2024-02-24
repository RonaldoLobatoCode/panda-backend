package org.example.panda.conductor.repositories;

import org.example.panda.conductor.entities.Conductor;
import org.example.panda.trabajador.entities.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Integer> {

}
