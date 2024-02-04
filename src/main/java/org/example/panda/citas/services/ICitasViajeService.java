package org.example.panda.citas.services;

import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.citas.dtos.CitasViajeDto;
import org.example.panda.citas.dtos.CitasViajeResponse;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;

public interface ICitasViajeService {

    CitasViajeDto createCitasViaje(CitasViajeDto citasViajeDto);
    CitasViajeResponse listCitasViaje(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    CitasViajeDto listCitasViajeById(Integer id);
    CitasViajeDto updateCitasViaje(Integer id, CitasViajeDto citasViajeDto);
    void deleteCitasViaje(Integer Id);
}
