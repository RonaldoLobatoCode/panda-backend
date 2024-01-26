package org.example.panda.aplicationSecurity.persistence.repositories;


import org.example.panda.aplicationSecurity.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT *FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);
}
