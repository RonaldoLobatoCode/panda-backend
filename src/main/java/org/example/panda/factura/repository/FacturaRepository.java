package org.example.panda.factura.repository;

import org.example.panda.factura.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

}
