package org.example.panda.trabajador.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.*;
import org.example.panda.trabajador.dtos.TrabajadorDto;
import org.example.panda.trabajador.dtos.TrabajadorResponse;
import org.example.panda.trabajador.services.ITrabajadorService;
import org.example.panda.utilities.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TrabajadorController {
    private final ITrabajadorService trabajadorService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    public TrabajadorController(ITrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }
    @GetMapping("/trabajadores")
    public ResponseEntity<TrabajadorResponse> listTrabajadores(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.NUMERO_PAG_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.MEDIDA_PAG_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDENAR_DIRECC_POR_DEFECTO, required = false) String sortDir){
        return new ResponseEntity<>(trabajadorService.listTrabajadores(numeroDePagina,medidaDePagina, ordenarPor, sortDir), HttpStatus.OK);
    }
    @GetMapping("/trabajador/{id}")
    public ResponseEntity<TrabajadorDto> findTrabajadorById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(trabajadorService.listTrabajadorById(id), HttpStatus.OK);
    }

    @PostMapping("trabajador")
    public ResponseEntity<TrabajadorDto> createTrabajadores(@Valid @RequestBody TrabajadorDto trabajadorDto){
        return new ResponseEntity<>(trabajadorService.createTrabajador(trabajadorDto), HttpStatus.CREATED);
    }
    @PutMapping("trabajador/{id}")
    public ResponseEntity<TrabajadorDto> updateTrabajador(@Valid @RequestBody TrabajadorDto trabajadorDto, @PathVariable("id") Integer id){
        return new ResponseEntity<>(trabajadorService.updateTrabajador(id, trabajadorDto), HttpStatus.OK);
    }

    @DeleteMapping("trabajador/{id}")
    public ResponseEntity<String> deleteTrabajador(@PathVariable("id") Integer id){
        trabajadorService.deleteTrabajador(id);
        return new ResponseEntity<>("Trabajador eliminado con éxito", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reportes/last")
    public void generarPDF(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:panda.jasper").getURI().getPath();

            final File logoEmpresa = ResourceUtils.getFile("classpath:images/logoEmpresa.jpg");
            final File imagenAlternativa = ResourceUtils.getFile("classpath:images/imagenAlternativa.png");

            // Crear un mapa para almacenar los valores de los parámetros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("logoEmpresa", new FileInputStream(logoEmpresa)); // Reemplaza "parametro1" y "valor1" con tus nombres de parámetro y valores
            parameters.put("imagenAlternativa", new FileInputStream(imagenAlternativa));

            // Llenar el informe Jasper con los parámetros proporcionados
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parameters, dataSource.getConnection());

            // Exportar el informe a PDF y enviarlo al navegador
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}