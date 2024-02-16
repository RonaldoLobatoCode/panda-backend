package org.example.panda.aplicationSecurity.controllers;

import org.example.panda.aplicationSecurity.services.IUserService;
import org.example.panda.aplicationSecurity.services.models.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> listUsers(){
        return ResponseEntity.ok().body(userService.findAll());
    }
}
