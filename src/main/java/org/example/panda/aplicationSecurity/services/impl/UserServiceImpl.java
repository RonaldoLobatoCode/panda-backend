package org.example.panda.aplicationSecurity.services.impl;

import lombok.AllArgsConstructor;
import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.persistence.repositories.UserRepository;
import org.example.panda.aplicationSecurity.services.IUserService;
import org.example.panda.aplicationSecurity.services.models.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }
    private UserDto entityToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
