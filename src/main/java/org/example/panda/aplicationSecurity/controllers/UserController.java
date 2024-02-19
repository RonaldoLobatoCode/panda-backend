package org.example.panda.aplicationSecurity.controllers;

import jakarta.validation.Valid;
import org.example.panda.aplicationSecurity.services.IUserService;
import org.example.panda.aplicationSecurity.services.models.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/users")
    ResponseEntity<List<UserRequest>> listUsers(){
        return ResponseEntity.ok().body(userService.findAll());
    }
    @PutMapping("user/{id}")
    ResponseEntity<UserRequest> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.update(id, userRequest));
    }
    @DeleteMapping("user/{id}")
    ResponseEntity<UserRequest> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.delete(id));
    }
}
