package org.example.panda.aplicationSecurity.controllers;

import jakarta.validation.Valid;
import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.services.IAuthService;
import org.example.panda.aplicationSecurity.services.models.dtos.LoginDto;
import org.example.panda.aplicationSecurity.services.models.dtos.SecurityResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth") //esta url es la que pusimos cuando creamos el secutityconfig, el auth tiene que estar definido, si no las rutas no serán públicas
public class AuthController {
    @Autowired
    IAuthService authService;

    @PostMapping("/register")
    private ResponseEntity<SecurityResponseDto> register(@Valid @RequestBody User userEntity) throws Exception {
        return new ResponseEntity<>(authService.register(userEntity), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    private ResponseEntity<HashMap<String, String>> login(@Valid @RequestBody LoginDto loginDTO) throws Exception {
        HashMap<String,String> loginHash = authService.login(loginDTO);
        if(loginHash.containsKey("jwt")){
            return new ResponseEntity<>(loginHash, HttpStatus.OK);
        }else {
            return  new ResponseEntity<>(loginHash,HttpStatus.UNAUTHORIZED);
        }
    }
}
