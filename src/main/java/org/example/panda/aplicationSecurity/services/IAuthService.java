package org.example.panda.aplicationSecurity.services;

import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.services.models.dtos.LoginDto;
import org.example.panda.aplicationSecurity.services.models.dtos.SecurityResponseDto;

import java.util.HashMap;

public interface IAuthService {
    public HashMap<String, String> login (LoginDto loginDTO) throws Exception;
    public SecurityResponseDto register(User user) throws Exception;
}
