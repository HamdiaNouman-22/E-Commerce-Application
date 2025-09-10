package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Cart;
import com.example.pinkbullmakeup.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
}
