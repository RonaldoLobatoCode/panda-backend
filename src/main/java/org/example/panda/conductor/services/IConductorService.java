package org.example.panda.conductor.services;


import org.example.panda.conductor.dtos.ConductorDto;
import org.example.panda.conductor.dtos.ConductorResponse;

public interface IConductorService {

    ConductorDto createConductor(ConductorDto conductorDto);
    ConductorResponse listConductor(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    ConductorDto listConductorById(Integer id);
    ConductorDto updateConductor(Integer id, ConductorDto conductorDto);
    void deleteConductor(Integer id);
}
