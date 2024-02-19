package org.example.panda.aplicationSecurity.services;

import org.example.panda.aplicationSecurity.services.models.request.UserRequest;

import java.util.List;

public interface IUserService {
    List<UserRequest> findAll();

    UserRequest update(Long id, UserRequest userRequest);
    UserRequest delete (Long id);
}

