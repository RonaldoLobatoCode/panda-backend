package org.example.panda.conductor.repositories;

import org.example.panda.conductor.entities.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamionRepository extends JpaRepository<Camion, Integer> {
}
