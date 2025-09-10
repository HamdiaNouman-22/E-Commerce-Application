package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByadminId(UUID id);
}
