package org.example.panda.guiaTransportista.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.persistence.repositories.UserRepository;
import org.example.panda.exceptions.ResourceNotFoundException;
import org.example.panda.feignClient.SunatClient;
import org.example.panda.feignClient.response.ReniecResponse;
import org.example.panda.feignClient.response.SunatResponse;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaDto;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaRequest;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponse;
import org.example.panda.guiaTransportista.dtos.GuiaTransportistaResponseById;
import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.guiaTransportista.repository.GuiaTransportistaRepository;
import org.example.panda.guiaTransportista.services.IGuiaTransportistaService;
import org.example.panda.trabajador.entities.Trabajador;
import org.example.panda.trabajador.repositories.TrabajadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
public class GuiaTransportistaImpl implements IGuiaTransportistaService {

    private final ModelMapper modelMapper;
    private final TrabajadorRepository trabajadorRepository;
    private final GuiaTransportistaRepository guiaTransportistaRepository;
    private final UserRepository userRepository;
    private final SunatClient sunatClient;
    @Value("${token.api}")
    private String tokenApi;
    private AtomicInteger ultimoNumeroGuia = new AtomicInteger(0);
    public GuiaTransportistaImpl(ModelMapper modelMapper, TrabajadorRepository trabajadorRepository, GuiaTransportistaRepository guiaTransportistaRepository, UserRepository userRepository, SunatClient sunatClient) {
        this.modelMapper = modelMapper;
        this.trabajadorRepository = trabajadorRepository;
        this.guiaTransportistaRepository = guiaTransportistaRepository;
        this.userRepository = userRepository;
        this.sunatClient = sunatClient;
    }

    @Override
    public GuiaTransportistaDto createGuiaTransportista(GuiaTransportistaRequest request) {
        SunatResponse responseRemitente=getExecution(request.getRemitenteRuc());
        if(responseRemitente==null){
            throw new IllegalArgumentException("el ruc remitente no está disponible!");
        }
        Optional<Trabajador> trabajador= trabajadorRepository.getTrabajadorByNumIdentidad(request.getNumDocChofer());
        User user= userRepository.getById(request.getIdUser());
        int nuevoNumeroGuia = ultimoNumeroGuia.incrementAndGet();
        GuiaTransportista guiaTransportista= GuiaTransportista.builder()
                .serieGuia("EG03")
                .numeroGuia(nuevoNumeroGuia)
                .partida(request.getPartida())
                .llegada(request.getLlegada())
                .fechaTraslado(request.getFechaTraslado())
                .remitenteRuc(request.getRemitenteRuc())
                .remitenteRazonSocial(responseRemitente.getRazonSocial())
                .remitenteDireccion(responseRemitente.getDireccion())
                .destinatarioRuc(request.getDestinatarioRuc())
                .destinatarioRazonSocial(request.getDestinatarioRazonSocial())
                .destinatarioDireccion(request.getDestinatarioDireccion())
                .pesoCarga(request.getPesoCarga())
                .numDocChofer(request.getNumDocChofer())
                .nombreChofer(trabajador.get().getNombres())
                .placaVehiculo(request.getPlacaVehiculo())
                .rucPagadorDelFlete(request.getRucPagadorDelFlete())
                .user(user)
                .build();
        GuiaTransportista save = guiaTransportistaRepository.save(guiaTransportista);
        return guiaEntityToDto(save);
    }

    @Override
    public GuiaTransportistaResponse listGuiaTransportista(int numeroDePagina, int medidaDePagina, String ordenarPor,
            String sortDir) {
        // TODO Auto-generated method stub
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<GuiaTransportista> guiaTransportista = guiaTransportistaRepository.findAll(pageable);

        List<GuiaTransportista> GuiaTransportistaList = guiaTransportista.getContent();
        List<GuiaTransportistaDto> contenido = GuiaTransportistaList.stream().map(this::guiaEntityToDto)
                .collect(Collectors.toList());

        return GuiaTransportistaResponse.builder()
                .contenido(contenido)
                .numeroPagina(guiaTransportista.getNumber())
                .medidaPagina(guiaTransportista.getSize())
                .totalElementos(guiaTransportista.getTotalElements())
                .totalPaginas(guiaTransportista.getTotalPages())
                .ultima(guiaTransportista.isLast())
                .build();
    }

    @Override
    public GuiaTransportistaResponseById listGuiaTransportistaById(Integer id) {
        // TODO Auto-generated method stub
        GuiaTransportista guiaTransportista = guiaTransportistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GuiaTransportista", "id", id));
        return GuiaTransportistaResponseById.builder()
                .id(guiaTransportista.getId())
                .numeroGuia(guiaTransportista.getNumeroGuia())
                .serieGuia(guiaTransportista.getSerieGuia())
                .fechaEmision(guiaTransportista.getFechaEmision())
                .partida(guiaTransportista.getPartida())
                .llegada(guiaTransportista.getLlegada())
                .fechaEmision(guiaTransportista.getFechaEmision())
                .fechaTraslado(guiaTransportista.getFechaTraslado())
                .remitenteRazonSocial(guiaTransportista.getRemitenteRazonSocial())
                .remitenteDireccion(guiaTransportista.getRemitenteDireccion())
                .remitenteRuc(guiaTransportista.getRemitenteRuc())
                .destinatarioRuc(guiaTransportista.getDestinatarioRuc())
                .destinatarioRazonSocial(guiaTransportista.getDestinatarioRazonSocial())
                .destinatarioDireccion(guiaTransportista.getDestinatarioDireccion())
                .pesoCarga(guiaTransportista.getPesoCarga())
                .numDocChofer(guiaTransportista.getNumDocChofer())
                .nombreChofer(guiaTransportista.getNombreChofer())
                .placaVehiculo(guiaTransportista.getPlacaVehiculo())
                .rucPagadorDelFlete(guiaTransportista.getRucPagadorDelFlete())
                .build();
    }


    private GuiaTransportistaDto guiaEntityToDto(GuiaTransportista guiaTransportista) {
        return modelMapper.map(guiaTransportista, GuiaTransportistaDto.class);
    }
    public SunatResponse getExecution(String numero){
        String authorization = "Bearer " + tokenApi;
        return sunatClient.getInfo(numero, authorization);
    }
}
