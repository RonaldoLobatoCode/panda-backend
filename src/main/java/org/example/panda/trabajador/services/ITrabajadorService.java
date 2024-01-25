package org.example.panda.trabajador.services;

import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.Trabajador;

import java.util.List;
import java.util.Optional;

public interface ITrabajadorService {

    TrabajadorDto createTrabajador(TrabajadorDto trabajadorDto);
    TrabajadorResponse listTrabajadores(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    Optional<TrabajadorDto> listTrabajadorById(Integer id);
    Optional<TrabajadorDto> updateTrabajador(Integer id, TrabajadorDto trabajadorDto);
    boolean deleteTrabajadorById(Integer Id);

}
