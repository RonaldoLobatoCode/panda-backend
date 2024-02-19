package org.example.panda.aplicationSecurity.services.impl;

import lombok.AllArgsConstructor;
import org.example.panda.aplicationSecurity.persistence.entities.ERole;
import org.example.panda.aplicationSecurity.persistence.entities.Role;
import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.example.panda.aplicationSecurity.persistence.repositories.RoleRepository;
import org.example.panda.aplicationSecurity.persistence.repositories.UserRepository;
import org.example.panda.aplicationSecurity.services.IUserService;
import org.example.panda.aplicationSecurity.services.models.request.UserRequest;
import org.example.panda.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    @Override
    public List<UserRequest> findAll() {
        return userRepository.findAllByEstado("activo").stream().map(this::entityToRequest).collect(Collectors.toList());
    }

    @Override
    public UserRequest update(Long id, UserRequest userRequest) {
        if(userRequest.getId()==null || id.equals(userRequest.getId())){
            validateUniqueValues(userRequest, id);
            User user= userRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("User", "id", id));
            Set<Role> roles= getRoles(requestToEntity(userRequest));
            user.setNombres(userRequest.getNombres());
            user.setApellidos(userRequest.getApellidos());
            user.setRoles(roles);
            user.setEmail(userRequest.getEmail());
            user.setNumIdentidad(userRequest.getNumIdentidad());
            user.setTelefono(userRequest.getTelefono());
            user.setUsername(userRequest.getUsername());
            return entityToRequest(userRepository.save(user));
        }
        throw new IllegalArgumentException("El ID enviado por la URL es distinto al del registro.");
    }

    @Override
    public UserRequest delete(Long id) {
        User user= userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Trabajador", "id", id));
        user.setEstado("inactivo");
        userRepository.save(user);
        return entityToRequest(user);
    }

    private UserRequest entityToRequest(User user){
        UserRequest userRequest= modelMapper.map(user, UserRequest.class);
        boolean isAdmin= user.getRoles().stream().anyMatch(r -> ERole.ADMIN.equals(r.getRole()));
        userRequest.setAdmin(isAdmin);
        return userRequest;
    }
    private User requestToEntity(UserRequest request){
        return modelMapper.map(request, User.class);
    }
    public void validateUniqueValues(UserRequest userRequest, Long id){
        List<User> User= userRepository.findAll();
        for (User tr : User) {
            if(tr.getEmail().equals(userRequest.getEmail()) && !tr.getId().equals(id)){
                throw new IllegalArgumentException("El email ya existe, por favor ingrese otro.");

            }else if(tr.getNumIdentidad().equals(userRequest.getNumIdentidad()) && !tr.getId().equals(id)){
                throw new IllegalArgumentException("El número de identidad ya existe, por favor ingrese otro.");
            } else if(tr.getUsername().equals(userRequest.getUsername()) && !tr.getId().equals(id))
                throw new IllegalArgumentException("El nombre de usuario ya existe, por favor ingrese otro.");
        }
    }
    private Set<Role> getRoles(User user){
        Optional<Role> ou = roleRepository.findById(2);
        Set<Role> roles = new HashSet<>();
        if (ou.isPresent()) {
            roles.add(ou.orElseThrow());
        }
        if(user.isAdmin()){
            Optional<Role> oa = roleRepository.findById(1);
            if (oa.isPresent()) {
                roles.add(oa.orElseThrow());
            }
        }
        return roles;
    }
}
