package org.example.panda.factura.services.impl;


import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.persistence.repositories.UserRepository;
import org.example.panda.factura.dtos.FacturaDto;
import org.example.panda.factura.dtos.FacturaRequest;
import org.example.panda.factura.entity.Factura;
import org.example.panda.factura.repository.FacturaRepository;
import org.example.panda.factura.services.FacturaService;
import org.example.panda.feignClient.SunatClient;
import org.example.panda.feignClient.response.SunatResponse;
import org.example.panda.guiaTransportista.entity.GuiaTransportista;
import org.example.panda.guiaTransportista.repository.GuiaTransportistaRepository;
import org.example.panda.item.entity.Item;
import org.example.panda.item.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FacturaServiceImpl implements FacturaService {
    private final BigDecimal IGV = BigDecimal.valueOf(0.18);
    private final ModelMapper modelMapper;
    private final FacturaRepository facturaRepository;
    private final GuiaTransportistaRepository guiaTransportistaRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final SunatClient sunatClient;
    @Value("${token.api}")
    private String tokenApi;
    private AtomicInteger ultimoNumeroFac = new AtomicInteger(0);

    public FacturaServiceImpl(ModelMapper modelMapper, FacturaRepository facturaRepository, GuiaTransportistaRepository guiaTransportistaRepository, UserRepository userRepository, ItemRepository itemRepository, SunatClient sunatClient) {
        this.modelMapper = modelMapper;
        this.facturaRepository = facturaRepository;
        this.guiaTransportistaRepository = guiaTransportistaRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.sunatClient = sunatClient;
    }

    @Override
    @Transactional
    public FacturaDto createFactura(FacturaRequest request) {
        // Obtener información del remitente desde Sunat
        SunatResponse responseRemitente = getExecution(request.getClienteRuc());
        validarRemitente(responseRemitente);

        // Obtener la guía transportista
        Optional<GuiaTransportista> guia = guiaTransportistaRepository.getGuiaTransportistaByNumeroGuia(request.getNumeroGuia());
        validarGuiaTransportista(guia);

        // Guardar los ítems de la factura
        List<Item> itemsSave = itemRepository.saveAll(request.getItems());

        // Calcular subtotal y total
        BigDecimal subTotal = calcularSubtotal(itemsSave);
        BigDecimal total = subTotal.add(subTotal.multiply(IGV));

        // Obtener información del usuario
        User user = userRepository.getById(request.getIdUser());

        // Generar número de factura
        int nuevoNumeroFac = ultimoNumeroFac.incrementAndGet();

        // Construir la factura
        Factura factura = construirFactura(responseRemitente, guia.get(), subTotal, total, user, nuevoNumeroFac, itemsSave, request);

        // Guardar y convertir la factura a DTO
        return facturaEntityToDto(facturaRepository.save(factura));
    }

// Funciones auxiliares

    private void validarRemitente(SunatResponse responseRemitente) {
        if (responseRemitente == null) {
            throw new IllegalArgumentException("El RUC del remitente no está disponible");
        }
    }

    private void validarGuiaTransportista(Optional<GuiaTransportista> guia) {
        if (guia.isEmpty()) {
            throw new IllegalArgumentException("El número de guía no está registrada. Por favor, ingrese un número válido");
        }
    }

    private BigDecimal calcularSubtotal(List<Item> itemsSave) {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (Item i : itemsSave) {
            BigDecimal cantidad = BigDecimal.valueOf(i.getCantidad());
            BigDecimal subtotalItem = cantidad.multiply(i.getPrecioUnitario());
            subTotal = subTotal.add(subtotalItem);
        }
        return subTotal;
    }
    private Factura construirFactura(SunatResponse responseRemitente, GuiaTransportista guia, BigDecimal subTotal, BigDecimal total, User user, int nuevoNumeroFac, List<Item> itemsSave, FacturaRequest request) {
        return Factura.builder()
                .serieFactura("E001")
                .numeroFactura(nuevoNumeroFac)
                .subTotal(subTotal)
                .igv(IGV)
                .montoTotal(total)
                .clienteRuc(request.getClienteRuc())
                .clienteRazonSocial(responseRemitente.getRazonSocial())
                .clienteDireccion(responseRemitente.getDireccion())
                .guiaTransportista(guia)
                .observacion(request.getObservacion())
                .user(user)
                .items(new HashSet<>(itemsSave))
                .build();
    }
    public SunatResponse getExecution(String numero){
        String authorization = "Bearer " + tokenApi;
        return sunatClient.getInfo(numero, authorization);
    }
    private FacturaDto facturaEntityToDto(Factura factura) {
        return modelMapper.map(factura, FacturaDto.class);
    }
}
