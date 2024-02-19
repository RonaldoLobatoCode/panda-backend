package org.example.panda.aplicationSecurity.controllers;

import jakarta.validation.Valid;
import org.example.panda.aplicationSecurity.persistence.entities.Role;
import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.persistence.repositories.RoleRepository;
import org.example.panda.aplicationSecurity.services.IAuthService;
import org.example.panda.aplicationSecurity.services.models.dtos.LoginDto;
import org.example.panda.aplicationSecurity.services.models.dtos.SecurityResponseDto;
import org.example.panda.aplicationSecurity.services.models.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth") //esta url es la que pusimos cuando creamos el secutityconfig, el auth tiene que estar definido, si no las rutas no serán públicas
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    @Autowired
    IAuthService authService;
    @PostMapping("/register")
    private ResponseEntity<SecurityResponseDto> register(@Valid @RequestBody UserDto userDto) throws Exception {

        return new ResponseEntity<>(authService.register(userDto), HttpStatus.CREATED);
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
