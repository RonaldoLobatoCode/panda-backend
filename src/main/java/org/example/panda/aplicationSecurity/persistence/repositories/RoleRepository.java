package org.example.panda.aplicationSecurity.persistence.repositories;

import org.example.panda.aplicationSecurity.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
