package org.example.panda.aplicationSecurity.persistence.repositories;

import org.example.panda.aplicationSecurity.persistence.entities.ERole;
import org.example.panda.aplicationSecurity.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(ERole role);
}
