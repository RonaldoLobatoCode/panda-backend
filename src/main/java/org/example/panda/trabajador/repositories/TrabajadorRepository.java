package org.example.panda.trabajador.repositories;

import org.example.panda.trabajador.entities.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
}
