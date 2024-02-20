package org.example.panda.aplicationSecurity.persistence.repositories;


import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT *FROM users WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(String username);
    @Query(value = "SELECT *FROM users WHERE estado = :estado", nativeQuery = true)
    List<User> findAllByEstado(String estado);
}
