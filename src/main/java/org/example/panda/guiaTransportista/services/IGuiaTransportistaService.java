package org.example.panda.guiaTransportista.services;

import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaRequest;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponseById;

public interface IGuiaTransportistaService {

    GuiaTransportistaDto createGuiaTransportista(GuiaTransportistaRequest request);

    GuiaTransportistaResponse listGuiaTransportista(int numeroDePagina, int medidaDePagina, String ordenarPor,
            String sortDir);

    GuiaTransportistaResponseById listGuiaTransportistaById(Integer id);
}
