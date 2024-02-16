package org.example.panda.aplicationSecurity.services;

import org.example.panda.aplicationSecurity.services.models.dtos.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> findAll();

}

