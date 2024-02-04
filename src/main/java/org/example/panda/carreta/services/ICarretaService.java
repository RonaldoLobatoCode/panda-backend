package org.example.panda.carreta.services;

import org.example.panda.carreta.dtos.CarretaDto;
import org.example.panda.carreta.dtos.CarretaResponse;

public interface ICarretaService {

    CarretaDto createCarreta(CarretaDto carretaDto);
    CarretaResponse listCarreta(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    CarretaDto listCarretaById(Integer id);
    CarretaDto updateCarreta(Integer id, CarretaDto carretaDto);
    void deleteCarreta(Integer id);
}
