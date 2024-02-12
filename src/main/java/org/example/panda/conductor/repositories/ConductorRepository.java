package org.example.panda.conductor.repositories;

import org.example.panda.conductor.entities.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Integer> {

    boolean existsByTrabajadorId(Integer trabajadorId);
    boolean existsByCamionId(Integer camionId);
}
