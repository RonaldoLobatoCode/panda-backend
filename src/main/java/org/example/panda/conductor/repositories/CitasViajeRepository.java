package org.example.panda.conductor.repositories;

import org.example.panda.citas.entity.CitasViaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitasViajeRepository extends JpaRepository<CitasViaje, Integer> {
}
