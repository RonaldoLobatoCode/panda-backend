package org.example.panda.camion.services;

import org.example.panda.camion.dtos.CamionDto;
import org.example.panda.camion.dtos.CamionResponse;

public interface ICamionService {

    CamionDto createCamion(CamionDto camionDto);
    CamionResponse listCamion(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    CamionDto listCamionById(Integer id);
    CamionDto updateCamion(Integer id, CamionDto camionDto);
    void deleteCamion(Integer id);
}

