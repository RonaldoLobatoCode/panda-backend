package org.example.panda.trabajador.services;

import org.example.panda.feignClient.response.ReniecResponse;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.entities.Trabajador;

import java.util.List;
import java.util.Optional;

public interface ITrabajadorService {

    TrabajadorDto createTrabajador(TrabajadorDto trabajadorDto);
    TrabajadorResponse listTrabajadores(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    ReniecResponse getInfoReniec(String numero);
    TrabajadorDto listTrabajadorById(Integer id);
    TrabajadorDto updateTrabajador(Integer id, TrabajadorDto trabajadorDto);
    void deleteTrabajador(Integer Id);

}
