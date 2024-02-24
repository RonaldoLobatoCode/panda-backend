package org.example.panda.guiaTransportista.repository;

import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.trabajador.entities.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuiaTransportistaRepository extends JpaRepository<GuiaTransportista, Integer> {
    @Query(value = "SELECT *FROM guias_transportista WHERE numero_guia = :numeroGuia", nativeQuery = true)
    Optional <GuiaTransportista> getGuiaTransportistaByNumeroGuia(int numeroGuia);
}
