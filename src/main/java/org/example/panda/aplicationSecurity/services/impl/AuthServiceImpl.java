package org.example.panda.aplicationSecurity.services.impl;

import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.persistence.repositories.UserRepository;
import org.example.panda.aplicationSecurity.services.IAuthService;
import org.example.panda.aplicationSecurity.services.IJWTUtilityService;
import org.example.panda.aplicationSecurity.services.models.dtos.LoginDto;
import org.example.panda.aplicationSecurity.services.models.dtos.SecurityResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;
    @Override
    public HashMap<String, String> login(LoginDto loginDTO) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<User> user= userRepository.findByEmail(loginDTO.getEmail());
            if(user.isEmpty()){
                jwt.put("error","User not registered");
                return jwt;
            }
            //verificar la contraseña
            if(verifyPassword(loginDTO.getPassword(), user.get().getPassword())){
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
            } else{
                jwt.put("error","Authentication failed");
            }
            return jwt;
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    @Override
    public SecurityResponseDto register(User user) throws Exception {
        try{
            SecurityResponseDto responseDTO = new SecurityResponseDto();
            List<User> getAllUsers= userRepository.findAll();
            for(User repearFields : getAllUsers){
                if(Objects.equals(repearFields.getEmail(), user.getEmail())){
                    responseDTO.setMessage("Ya existe un usuario asociado al mismo correo electrónico!");
                    return responseDTO;
                }
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);//encriptamos la contraseña
            user.setPassword(encoder.encode(user.getPassword())); //y se ña asignamos al usuario
            userRepository.save(user);
            responseDTO.setMessage("Usuario creado exitosamente!");
            return responseDTO;
        }catch (Exception e){
            throw  new Exception(e.toString());
        }
    }
    private boolean verifyPassword(String enteredPassword, String storedPassword){
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword); //si hace match true, si no false
    }
}
