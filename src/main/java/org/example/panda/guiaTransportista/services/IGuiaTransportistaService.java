package org.example.panda.guiaTransportista.services;

import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;

public interface IGuiaTransportistaService {

    GuiaTransportistaDto createGuiaTransportista(GuiaTransportistaDto guiaTransportistaDto);

    GuiaTransportistaResponse listGuiaTransportista(int numeroDePagina, int medidaDePagina, String ordenarPor,
            String sortDir);

    GuiaTransportistaDto listGuiaTransportistaById(Integer id);

    GuiaTransportistaDto updateGuiaTransportista(Integer id, GuiaTransportistaDto guiaTransportistaDto);

    void deleteGuiaTransportista(Integer Id);
}
