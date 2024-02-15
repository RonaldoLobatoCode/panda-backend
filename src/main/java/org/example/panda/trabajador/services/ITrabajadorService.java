package org.example.panda.trabajador.services;

import jakarta.servlet.http.HttpServletResponse;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.Trabajador;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITrabajadorService {

    TrabajadorDto createTrabajador(TrabajadorDto trabajadorDto);
    TrabajadorResponse listTrabajadores(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    TrabajadorDto listTrabajadorById(Integer id);
    TrabajadorDto updateTrabajador(Integer id, TrabajadorDto trabajadorDto);
    void deleteTrabajador(Integer Id);

}
