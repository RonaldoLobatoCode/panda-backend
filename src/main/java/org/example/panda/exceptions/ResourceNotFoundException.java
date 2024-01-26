package org.example.panda.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value= HttpStatus.NOT_FOUND) //cuando llame a esta clase devolverá not found
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID=1L;
    private String nombreDelRecurso;
    private String nombreDelCampo;
    private long valorDelCampo;
    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelCampo) {
        super(String.format("%s no encontrado/a con : %s : '%s'", nombreDelRecurso, nombreDelCampo, valorDelCampo));
        //los signos %s sirven para indicarle que trabajaremos con parámetros
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;

    }
}
