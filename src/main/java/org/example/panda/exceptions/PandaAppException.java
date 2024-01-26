package org.example.panda.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class PandaAppException extends RuntimeException{
    private static final long serialVersionUID=1L;

    private HttpStatus estado;
    private String mensaje;

}
